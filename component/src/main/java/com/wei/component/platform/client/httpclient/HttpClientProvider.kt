package com.wei.component.platform.client.httpclient

import com.wei.component.platform.client.store.HttpStore
import okhttp3.OkHttpClient

/**
 * v1.0 of the file created on 2019-08-02 by shuxin.wei, email: weishuxin@maoyan.com
 * description: 网络连接层的提供者，向上层提供HttpClient
 */
class HttpClientProvider private constructor() : IHttpClientProvider {
    companion object {
        val simpleHttpClientLock = Any()
        val imageHttpClientLock = Any()
        val instance: HttpClientProvider by lazy {
            HttpClientProvider()
        }
    }

    override fun getSampleHttpClient(okHttpClient: OkHttpClient?) =
            HttpStore.defaultHttpClientStore.get(SimpleHttpFactory::class.java.name) ?: synchronized(simpleHttpClientLock) {
                HttpStore.defaultHttpClientStore.put(SimpleHttpFactory::class.java.name, SimpleHttpFactory(true).create(okHttpClient))
            }

    override fun getImageHttpClient(okHttpClient: OkHttpClient?) =
            HttpStore.defaultHttpClientStore.get(ImageHttpFactory::class.java.name) ?: synchronized(imageHttpClientLock) {
                HttpStore.defaultHttpClientStore.put(ImageHttpFactory::class.java.name, ImageHttpFactory(true).create(okHttpClient))
            }
}

interface IHttpClientProvider {
    fun getSampleHttpClient(okHttpClient: OkHttpClient? = null): OkHttpClient
    fun getImageHttpClient(okHttpClient: OkHttpClient? = null): OkHttpClient
}