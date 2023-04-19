package com.mikyegresl.valostat.network.util

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

internal object RetrofitWithFactories {

    fun create(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        converterFactories: List<Converter.Factory>
    ): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(okHttpClient)
            converterFactories.forEach {
                addConverterFactory(it)
            }
        }.build()
}
