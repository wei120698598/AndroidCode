package com.wei.component.platform.client.dns

import com.wei.component.platform.client.store.HttpStore
import com.wei.component.platform.client.store.Store
import okhttp3.Dns
import java.net.InetAddress

class HttpDns private constructor(
    val dnsStore: HttpStore<String, List<InetAddress>> = HttpStore.defaultDnsStore
) : Dns, Store<String, List<InetAddress>> {

    companion object {
        val instance: HttpDns by lazy {
            HttpDns()
        }
    }

    override fun lookup(hostname: String): List<InetAddress> {
        return Dns.SYSTEM.lookup(hostname)
    }

    override fun put(hostName: String, address: List<InetAddress>) = dnsStore.put(hostName, address)

    override fun get(hostName: String): List<InetAddress>? {
        return dnsStore.get(hostName) ?: lookup(hostName)
    }

    override fun remove(hostName: String) = dnsStore.remove(hostName)
}
