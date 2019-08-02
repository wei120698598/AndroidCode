package com.zanfou.component.platform.client.security

import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * v1.0 of the file created on 2019-08-01 by shuxin.wei, email: weishuxin@maoyan.com
 * description: 网络安全认证等管理器
 */
class HttpSecurityManager private constructor() {
    companion object {
        val instance: HttpSecurityManager by lazy {
            HttpSecurityManager()
        }
    }

    fun createHostnameVerifier(): HostnameVerifier =
        TrustAllHostnameVerifier()


    fun createSSLSocketFactory(): SSLSocketFactory {

        val sc = SSLContext.getInstance("TLS")
        sc.init(null, arrayOf<TrustManager>(TrustAllManager()), SecureRandom())
        return sc.socketFactory
    }

    fun createX509TrustManager(): X509TrustManager = TrustAllManager()
}


private class TrustAllManager : X509TrustManager {
    @Throws(CertificateException::class)
    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate?> {
        return arrayOfNulls(0)
    }
}

private class TrustAllHostnameVerifier : HostnameVerifier {
    override fun verify(hostname: String, session: SSLSession) = true
}
