package com.zanfou.component.platform.retrofit

import com.zanfou.component.platform.client.httpclient.HttpClientProvider
import com.zanfou.component.platform.client.json.GsonFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory : Factory {
    override fun create(baseUrl: String, baseRetrofit: Retrofit?): Retrofit =
        (baseRetrofit?.newBuilder() ?: Retrofit.Builder()).apply {
            addConverterFactory(GsonConverterFactory.create(GsonFactory().createGson()))
            client(HttpClientProvider.instance.getSampleHttpClient())
        }.build()

}


interface Factory {
    fun create(baseUrl: String, baseRetrofit: Retrofit? = null): Retrofit
}