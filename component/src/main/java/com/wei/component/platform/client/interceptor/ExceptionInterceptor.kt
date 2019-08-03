package com.wei.component.platform.client.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}