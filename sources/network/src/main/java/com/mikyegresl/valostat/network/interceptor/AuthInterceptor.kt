package com.mikyegresl.valostat.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor : Interceptor {

    companion object {
        private const val API_KEY_NAME = "key"
        private const val API_KEY_VALUE = "AIzaSyDPx-rMuTF8OaN4efmMzqY9lGFIDZeX_xo"
    }
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter(API_KEY_NAME, API_KEY_VALUE).build()
        request = request().newBuilder().url(url).build()

        proceed(
            request
        )
    }
}