package com.wei.sample.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class SimpleHttpClient1 {
    fun getHttpClient(httpClient: OkHttpClient): OkHttpClient {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
    }


}

fun main() {

    var a = 1
    var b = 2

    assert(a == b){
        print("aaaaaa")
    }

    println("1111")

}