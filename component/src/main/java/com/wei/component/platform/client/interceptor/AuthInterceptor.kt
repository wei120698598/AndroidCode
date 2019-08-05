package com.wei.component.platform.client.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.net.ResponseCache
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * v1.0 of the file created on 2019-08-02 by shuxin.wei, email: weishuxin@maoyan.com
 * description: 授权相关拦截器，例如401 AccessToken刷新，RefreshToken 过期处理
 */
abstract class AuthInterceptor : Interceptor {
    private var accessToken: String? = null
    private var refreshToken: String? = null
    @Volatile
    private var isTokenRefreshing = false
    private val lock = Any()
    private val readWriteLock = ReentrantReadWriteLock()
    private val readLock = readWriteLock.readLock()
    private val writeLock = readWriteLock.writeLock()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.header("auth")?.equals("true", true) == false) {
            return chain.proceed(request)
        }
        if (!isTokenRefreshing) {
            val response = chain.proceed(request)
            //没有报401 或者 不使用授权认证 ，直接返回Response
            if (response.code != 401) {
                return response
            }
        }

        //发生授权无效
        //判断是否使用AccessToken
        //判断是否正在请求刷新Token
        //如果使用了AccessToken，并且没有在刷新Token，那么开始执行Token刷新
        //同步锁，阻塞其他并发请求也出现401的，防止重复刷新Token
        synchronized(lock) {
            val refreshToken = getRefreshToken()
            if (!isTokenRefreshing && !refreshToken.isNullOrBlank()) {
                try {
                    isTokenRefreshing = true
                    this.accessToken = null
                    val tokenResponse = chain.refreshToken(refreshToken)
                    //如果刷新Token时又出现401，说明RefreshToken过期，需要清空Token，并重新登录
                    //如果能正常解析出来Token，那么就重新发起请求，如果不是401，并且没有解析成功，那么就返回默认的tokenResponse
                    if (tokenResponse.code == 200) {
                        val newToken = parseToken(tokenResponse)
                        if (newToken == null || newToken.accessToken.isNullOrBlank() || !newToken.refreshToken.isNullOrBlank()) {
                            clearAccessToken()
                            return tokenResponse
                        } else {
                            saveToken2DiskStore(newToken.accessToken!!, newToken.refreshToken!!)
                        }
                    } else {
                        clearAccessToken()
                        if (tokenResponse.code == 401) clearRefreshToken()
                        return tokenResponse
                    }
                } finally {
                    isTokenRefreshing = false
                }
            }
        }

        //重新设置accessToken发起请求
        val accessToken = getAccessToken()
        if (!isTokenRefreshing && !accessToken.isNullOrBlank()) {
            val newRequest = request.newBuilder()
            newRequest.header("accessToken", accessToken)
            return chain.proceed(newRequest.build())
        }

        //执行到这，说明刷新Token失败
        //构造AccessToken刷新失败的响应
        //TODO 构造刷新失败的响应
        return Response.Builder().build()
    }


    data class Token(var accessToken: String?, var refreshToken: String?)


    private fun getAccessToken(): String? {
        return if (accessToken != null) accessToken else {
            try {
                readLock.lock()
                getToken()?.let {
                    this.accessToken = it.accessToken
                    this.refreshToken = it.refreshToken
                    it.accessToken
                }
            } finally {
                readLock.unlock()
            }
        }
    }

    private fun getRefreshToken(): String? {
        return if (refreshToken != null) refreshToken else {
            try {
                readLock.lock()
                getToken()?.let {
                    this.accessToken = it.accessToken
                    this.refreshToken = it.refreshToken
                    it.refreshToken
                }
            } finally {
                readLock.unlock()
            }
        }
    }

    private fun saveToken2DiskStore(newAccessToken: String, newRefreshToken: String) {
        try {
            writeLock.lock()
            if (saveAccessToken(newAccessToken, newRefreshToken)) {
                this.accessToken = newAccessToken
                this.refreshToken = newRefreshToken
            }
        } finally {
            writeLock.unlock()
        }
    }

    private fun clearAccessTokenOfDiskStore() {
        try {
            writeLock.lock()
            clearAccessToken()
        } finally {
            writeLock.unlock()
        }
    }

    private fun clearRefreshTokenOfDiskStore() {
        try {
            writeLock.lock()
            clearRefreshToken()
        } finally {
            writeLock.unlock()
        }
    }


    /**
     * 使用[refreshToken]请求刷新[accessToken]
     */
    abstract fun Interceptor.Chain.refreshToken(refreshToken: String): Response

    /**
     * 获取包含[refreshToken]和[accessToken]的[Token]对象
     */
    abstract fun getToken(): Token?

    /**
     * 清除[Token]
     */
    abstract fun clearAccessToken(): Boolean

    abstract fun clearRefreshToken(): Boolean

    /**
     * 保存[Token]
     */
    abstract fun saveAccessToken(accessToken: String, refreshToken: String): Boolean

    /**
     * 从[response]中解析出[Token]
     */
    abstract fun parseToken(response: Response): Token?

}