package com.zanfou.component.platform.client.httpclient

import com.zanfou.component.platform.client.dns.HttpDns
import com.zanfou.component.platform.client.interceptor.InterceptorProvider
import com.zanfou.component.platform.client.security.HttpSecurityManager
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 通用Http连接池
 */
val SIMPLE_CONNECTION_POOL = ConnectionPool()
/**
 * 图片请求连接池
 */
val IMAGE_CONNECTION_POOL = ConnectionPool()
/**
 * IO超时时间
 */
const val SOCKET_WRITE_TIME_OUT = 15_000L
/**
 * 连接超时时间
 */
const val SOCKET_TIME_OUT = 15_000L
/**
 * 响应超时时间
 */
const val SOCKET_RESPONSE_TIME_OUT = 15_000L
/**
 * Http缓存大小缓存大小为10M
 */
const val CACHE_SIZE = 50 * 1_024L * 1_024L


/**
 * v1.0 of the file created on 2019-08-01 by shuxin.wei, email: weishuxin@maoyan.com
 * [SimpleHttpFactory]用于创建普通网络请求的[OkHttpClient]，创建的时候可以基于一个[OkHttpClient]；
 *
 * SSL 认证目前信任了所有的证书，只做了单向认证；
 *
 * HostName 校验需要在
 */
class SimpleHttpFactory(
    val debug: Boolean = true,
    val connectionPool: ConnectionPool = SIMPLE_CONNECTION_POOL,
    val cacheDir: File? = null
) : IHttpFactory {
    private val interceptorProvider: InterceptorProvider = InterceptorProvider.instance
    private val sslManager: HttpSecurityManager = HttpSecurityManager.instance

    /**
     * 创建OkHttpBuilder
     */
    override fun create(baseOkHttpClient: OkHttpClient?) =
        (baseOkHttpClient?.newBuilder() ?: OkHttpClient.Builder()).apply {
            //设置拦截器
            if (debug) {
                addInterceptor(interceptorProvider.getLoggingInterceptor())
                addNetworkInterceptor(interceptorProvider.getAuthInterceptor())
            }
            addInterceptor(interceptorProvider.getCacheInterceptor())
            addInterceptor(interceptorProvider.getExceptionInterceptor())
            addInterceptor(interceptorProvider.getHeaderInterceptor())

            //设置TimeOut
            writeTimeout(SOCKET_WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
            connectTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
            readTimeout(SOCKET_RESPONSE_TIME_OUT, TimeUnit.MILLISECONDS)

            //安全认证
            sslSocketFactory(sslManager.createSSLSocketFactory(), sslManager.createX509TrustManager())
            hostnameVerifier(sslManager.createHostnameVerifier())

            //设置DNS
            dns(HttpDns.instance)

            //设置失败重试
            retryOnConnectionFailure(true)

            //设置连接池
            connectionPool(connectionPool)

            //设置Cache位置
            cacheDir?.let {
                cache(Cache(cacheDir, CACHE_SIZE))
            }
        }.build()
}

class ImageHttpFactory(
    val debug: Boolean = true,
    val connectionPool: ConnectionPool = IMAGE_CONNECTION_POOL,
    val cacheDir: File? = null
) : IHttpFactory {
    private val interceptorProvider: InterceptorProvider = InterceptorProvider.instance
    private val sslManager: HttpSecurityManager = HttpSecurityManager.instance

    /**
     * 创建OkHttpBuilder
     */
    override fun create(baseOkHttpClient: OkHttpClient?) =
        (baseOkHttpClient?.newBuilder() ?: OkHttpClient.Builder()).apply {
            //设置拦截器
            if (debug) {
                addInterceptor(interceptorProvider.getLoggingInterceptor())
                addNetworkInterceptor(interceptorProvider.getAuthInterceptor())
            }
            addInterceptor(interceptorProvider.getHeaderInterceptor())

            //设置TimeOut
            writeTimeout(SOCKET_WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
            connectTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
            readTimeout(SOCKET_RESPONSE_TIME_OUT, TimeUnit.MILLISECONDS)

            //安全认证
            sslSocketFactory(
                sslManager.createSSLSocketFactory(),
                sslManager.createX509TrustManager()
            )
            hostnameVerifier(sslManager.createHostnameVerifier())

            //设置失败重试
            retryOnConnectionFailure(true)

            //设置连接池
            connectionPool(connectionPool)

            //设置Cache位置
            cacheDir?.let {
                cache(Cache(cacheDir, CACHE_SIZE))
            }
        }.build()
}

/**
 * [OkHttpClient]工厂，例如创建通用网络请求的 Client ，或者配合[Glide]加载的 Client
 */
interface IHttpFactory {
    fun create(baseOkHttpClient: OkHttpClient?): OkHttpClient
}