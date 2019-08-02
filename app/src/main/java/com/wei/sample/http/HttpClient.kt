package com.wei.sample.http

import okhttp3.OkHttpClient

interface HttpClient {
    companion object {
        val defaultHttpClient = OkHttpClient.Builder().build()
    }

    fun getHttpClient(httpClient: OkHttpClient): OkHttpClient
}