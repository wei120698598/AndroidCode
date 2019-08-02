package com.zanfou.component.platform.client.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * v1.0 of the file created on 2019-08-02 by shuxin.wei, email: weishuxin@maoyan.com
 * description: 授权相关拦截器，例如401 AccessToken刷新，RefreshToken 过期处理
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}