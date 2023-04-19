package com.mikyegresl.valostat.network.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal class OkHttpClientBuilder {

    private val networkInterceptors = mutableListOf<Interceptor>()

    var connectTimeoutSec = CONNECT_TIMEOUT_SEC
    var readTimeoutSec = READ_TIMEOUT_SEC
    var writeTimeoutSec = WRITE_TIMEOUT_SEC

    val interceptors = mutableListOf<Interceptor>()

    fun build(construct: OkHttpClientBuilder.() -> Unit): OkHttpClient =
        initializeBuilder(OkHttpClient.Builder(), construct).build()

    private fun initializeBuilder(
        builder: OkHttpClient.Builder,
        construct: OkHttpClientBuilder.() -> Unit
    ): OkHttpClient.Builder {
        this.construct()
        builder.apply {
            connectTimeout(connectTimeoutSec, TimeUnit.SECONDS)
            readTimeout(readTimeoutSec, TimeUnit.SECONDS)
            writeTimeout(writeTimeoutSec, TimeUnit.SECONDS)
            interceptors.forEach { addInterceptor(it) }
            networkInterceptors.forEach { addNetworkInterceptor(it) }
        }
        return builder
    }

    companion object {

        const val CONNECT_TIMEOUT_SEC = 60L
        const val READ_TIMEOUT_SEC = 30L
        const val WRITE_TIMEOUT_SEC = 15L
    }
}
