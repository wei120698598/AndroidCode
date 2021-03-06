package com.wei.component.platform.client.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor(private var headers: Map<String, String>? = null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(newRequestBuilder(chain.request()).build())
    }

    private fun newRequestBuilder(request: Request?): Request.Builder {
        val newBuilder = request?.newBuilder() ?: Request.Builder()
        headers?.map {
            newBuilder.header(it.key, it.value)
        }
        return newBuilder
                .addHeader("appEnvironment", "")
                .addHeader("buildVer", "")
                //增加close链接避免io异常  http://www.jianshu.com/p/dea2ffb1c3b1
                .addHeader("Connection", "close")
    }
}