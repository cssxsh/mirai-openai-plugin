package xyz.cssxsh.openai

import io.ktor.http.*
import okhttp3.Dns
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import okhttp3.internal.tls.OkHostnameVerifier
import java.net.*
import java.security.*
import java.security.cert.*
import javax.net.ssl.*

internal fun OkHttpClient.Builder.apply(config: OpenAiClientConfig) {
    dns(object : Dns {
        private val doh = DnsOverHttps.Builder()
            .client(OkHttpClient())
            .url(config.doh.toHttpUrl())
            .includeIPv6(config.ipv6)
            .build()

        override fun lookup(hostname: String): List<InetAddress> {
            if (hostname == "api.openai.com" && config.proxy.isEmpty()) return lookup("msly.api.openai.com")
            return try {
                doh.lookup(hostname)
            } catch (_: UnknownHostException) {
                Dns.SYSTEM.lookup(hostname)
            } catch (cause: Exception) {
                throw UnknownHostException(hostname).initCause(cause)
            }
        }
    })
    proxy(config.proxy.ifEmpty {
        sslSocketFactory(FakeSSLSocketFactory, FakeX509TrustManager)
        hostnameVerifier(FakeHostnameVerifier)
        null
    }?.let { urlString ->
        val url = Url(urlString)
        when (url.protocol) {
            URLProtocol.HTTP -> Proxy(Proxy.Type.HTTP, InetSocketAddress(url.host, url.port))
            URLProtocol.SOCKS -> Proxy(Proxy.Type.SOCKS, InetSocketAddress(url.host, url.port))
            else -> throw IllegalArgumentException("Illegal Proxy: $urlString")
        }
    })
}

private object FakeSSLSocketFactory : SSLSocketFactory() {

    private fun Socket.setServerNames(): Socket = apply {
        if (this !is SSLSocket) return@apply
        sslParameters = sslParameters.apply {
            if ((serverNames.singleOrNull() as? SNIHostName)?.asciiName == "api.openai.com") {
                serverNames = listOf(SNIHostName("beta.openai.com"))
            }
        }
    }

    private val default: SSLSocketFactory by lazy {
        SSLContext.getDefault().socketFactory
    }

    override fun createSocket(socket: Socket?, host: String?, port: Int, autoClose: Boolean): Socket? =
        default.createSocket(socket, host, port, autoClose)?.setServerNames()

    override fun createSocket(host: String?, port: Int): Socket? =
        default.createSocket(host, port)?.setServerNames()

    override fun createSocket(host: String?, port: Int, localHost: InetAddress?, localPort: Int): Socket? =
        default.createSocket(host, port, localHost, localPort)?.setServerNames()

    override fun createSocket(host: InetAddress?, port: Int): Socket? =
        default.createSocket(host, port)?.setServerNames()

    override fun createSocket(address: InetAddress?, port: Int, localAddress: InetAddress?, localPort: Int): Socket? =
        default.createSocket(address, port, localAddress, localPort)?.setServerNames()

    override fun getDefaultCipherSuites(): Array<String> = default.defaultCipherSuites

    override fun getSupportedCipherSuites(): Array<String> = default.supportedCipherSuites
}

private object FakeX509TrustManager : X509TrustManager {

    private val delegate: X509TrustManager by lazy {
        val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        factory.init(null as KeyStore?)
        factory.trustManagers.filterIsInstance<X509TrustManager>().first()
    }

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}

    override fun getAcceptedIssuers(): Array<X509Certificate> = delegate.acceptedIssuers
}

private object FakeHostnameVerifier : HostnameVerifier {
    override fun verify(hostname: String, session: SSLSession): Boolean {
        if (hostname.endsWith("openai.com")) return true
        return OkHostnameVerifier.verify(hostname, session)
    }
}