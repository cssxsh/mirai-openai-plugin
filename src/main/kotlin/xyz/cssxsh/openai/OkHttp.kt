package xyz.cssxsh.openai

import okhttp3.Dns
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.UnknownHostException

internal fun OkHttpClient.Builder.apply(config: OpenAiClientConfig) {
    dns(object : Dns {
        private val doh = DnsOverHttps.Builder()
            .client(OkHttpClient())
            .url(config.doh.toHttpUrl())
            .includeIPv6(config.ipv6)
            .build()

        override fun lookup(hostname: String): List<InetAddress> {
            return try {
                doh.lookup(hostname)
            } catch (_: UnknownHostException) {
                Dns.SYSTEM.lookup(hostname)
            } catch (cause: Exception) {
                throw UnknownHostException(hostname).initCause(cause)
            }
        }
    })
    proxy(config.proxy.toHttpUrlOrNull()?.let { url ->
        when (url.scheme) {
            "socks" -> Proxy(Proxy.Type.SOCKS, InetSocketAddress(url.host, url.port))
            "http" -> Proxy(Proxy.Type.HTTP, InetSocketAddress(url.host, url.port))
            else -> Proxy.NO_PROXY
        }
    })
}