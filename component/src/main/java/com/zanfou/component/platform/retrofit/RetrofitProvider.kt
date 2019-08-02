package com.zanfou.component.platform.retrofit

import com.zanfou.component.platform.client.store.HttpStore
import retrofit2.Retrofit

class RetrofitProvider private constructor(
    private val retrofitFactory: RetrofitFactory = RetrofitFactory(),
    private val retrofitStore: HttpStore<String, Retrofit> = HttpStore.defaultRetrofitStore
) : IRetrofitProvider {
    companion object {

        val instance: RetrofitProvider by lazy {
            RetrofitProvider()
        }
    }

    override fun getRetrofit(baseUrl: String, retrofit: Retrofit?): Retrofit =
        retrofitStore.get(baseUrl) ?: synchronized(RetrofitProvider::class) {
            retrofitFactory.create(baseUrl, retrofit)
        }
}

interface IRetrofitProvider {
    fun getRetrofit(baseUrl: String, retrofit: Retrofit? = null): Retrofit
}