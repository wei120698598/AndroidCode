package com.zanfou.component.platform.client.interceptor

import com.zanfou.component.platform.client.store.HttpStore
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

class InterceptorProvider private constructor(private val interceptorStore: HttpStore<String, Interceptor> = HttpStore.defaultInterceptorStore) :
    IInterceptorProvider, HttpLoggingInterceptor.Logger {
    override fun log(message: String) {

    }


    companion object {
        val instance: InterceptorProvider by lazy {
            InterceptorProvider()
        }
    }

    override fun getLoggingInterceptor(): Interceptor {
        val key = HttpLoggingInterceptor::class.java.name
        return interceptorStore.get(key) ?: interceptorStore.put(key, HttpLoggingInterceptor(this))
    }

    override fun getHeaderInterceptor(): Interceptor {
        val key = HeaderInterceptor::class.java.name
        return interceptorStore.get(key) ?: interceptorStore.put(key, HeaderInterceptor())
    }

    override fun getAuthInterceptor(): Interceptor {
        val key = AuthInterceptor::class.java.name
        return interceptorStore.get(key) ?: interceptorStore.put(key, AuthInterceptor())
    }

    override fun getExceptionInterceptor(): Interceptor {
        val key = ExceptionInterceptor::class.java.name
        return interceptorStore.get(key) ?: interceptorStore.put(key, ExceptionInterceptor())
    }

    override fun getCacheInterceptor(): Interceptor {
        val key = CacheInterceptor::class.java.name
        return interceptorStore.get(key) ?: interceptorStore.put(key, CacheInterceptor())
    }
}


interface IInterceptorProvider {
    fun getLoggingInterceptor(): Interceptor
    fun getHeaderInterceptor(): Interceptor
    fun getAuthInterceptor(): Interceptor
    fun getExceptionInterceptor(): Interceptor
    fun getCacheInterceptor(): Interceptor
}