package com.wei.component.platform.client.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * v1.0 of the file created on 2019-08-02 by shuxin.wei, email: weishuxin@maoyan.com
 * description: 授权相关拦截器，例如401 AccessToken刷新，RefreshToken 过期处理
 */
class AuthInterceptor : Interceptor {
    @Volatile
    private var isTokenRefreshing = false
    private val lock = Any()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        //发生授权无效
        if (response.code == 401) {
            val isAuth = request.header("auth")?.equals("true", true) == true
            //判断是否使用AccessToken
            //判断是否正在请求刷新Token
            //如果使用了AccessToken，并且没有在刷新Token，那么开始执行Token刷新
            if (isAuth) {
                //同步锁，阻塞其他并发请求也出现401的，防止重复刷新Token
                synchronized(lock) {
                    val refreshToken = getRefreshToken()
                    if (!isTokenRefreshing && !refreshToken.isNullOrBlank()) {
                        try {
                            isTokenRefreshing = true
                            val tokenResponse = chain.refreshToken(refreshToken)
                            //如果刷新Token时又出现401，说明RefreshToken过期，需要重新登录
                            if (tokenResponse.code == 200) {
                                saveAccessToken("accessToken")
                            } else {
                                clearToken()
                            }
                        } finally {
                            isTokenRefreshing = false
                        }
                    }
                }
                val accessToken = getAccessToken()
                if (!isTokenRefreshing && !accessToken.isNullOrBlank()) {
                    val newRequest = request.newBuilder()
                    newRequest.header("accessToken", accessToken)
                    return chain.proceed(newRequest.build())
                }
            }
        }
        return response
    }


    private fun Interceptor.Chain.refreshToken(refreshToken: String) = proceed(Request.Builder()
            .url("tokenUrl....")
            .header("refreshToken", refreshToken)
            .build())


    private fun getAccessToken(): String? {
        return ""
    }

    private fun getRefreshToken(): String? {
        return ""
    }

    private fun clearToken() {

    }

    private fun saveAccessToken(accessToken: String) {

    }

    private fun saveRefreshToken(refreshToken: String) {

    }
}