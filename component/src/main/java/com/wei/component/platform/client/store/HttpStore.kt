package com.wei.component.platform.client.store

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.net.InetAddress
import java.util.concurrent.ConcurrentHashMap

/**
 * v1.0 of the file created on 2019-08-01 by shuxin.wei, email: weishuxin@maoyan.com
 * description: 网络组件的缓存器
 */
class HttpStore<K, V> : Store<K, V>, Clear<K, V> {
    companion object {
        val defaultHttpClientStore: HttpStore<String, OkHttpClient> by lazy {
            HttpStore<String, OkHttpClient>()
        }
        val defaultInterceptorStore: HttpStore<String, Interceptor> by lazy {
            HttpStore<String, Interceptor>()
        }

        val defaultDnsStore: HttpStore<String, List<InetAddress>> by lazy {
            HttpStore<String, List<InetAddress>>()
        }

        val defaultRetrofitStore: HttpStore<String, Retrofit> by lazy {
            HttpStore<String, Retrofit>()
        }
    }

    private val storeMap = ConcurrentHashMap<K, V>()

    override fun put(key: K, value: V): V {
        storeMap[key] = value
        return value
    }

    override fun get(key: K) = storeMap[key]

    override fun remove(key: K) = storeMap.remove(key)

    override fun clear() {
        storeMap.clear()
    }
}


interface Store<K, V> {
    fun put(key: K, value: V): V
    fun get(key: K): V?
    fun remove(key: K): V?
}


interface Clear<K, V> {
    fun clear()
}