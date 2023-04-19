package com.mikyegresl.valostat.network

import com.mikyegresl.valostat.base.network.service.WeaponsRemoteDataSource
import com.mikyegresl.valostat.network.api.ValorantApi
import com.mikyegresl.valostat.base.network.config.NetworkConfig
import com.mikyegresl.valostat.network.error.NetworkErrorHandler
import com.mikyegresl.valostat.network.interceptor.HeaderInterceptor
import com.mikyegresl.valostat.network.service.WeaponsRemoteDataSourceImpl
import com.mikyegresl.valostat.network.util.OkHttpClientBuilder
import com.mikyegresl.valostat.network.util.RetrofitWithFactories
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkFactory(
    private val config: NetworkConfig
) {

    private val headerInterceptor by lazy { HeaderInterceptor() }

    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    private val baseOkHttpClient by lazy {
        OkHttpClientBuilder().build {
            if (config.isDebug) {
                interceptors.add(httpLoggingInterceptor)
            }
        }
    }

    private val okHttpClient by lazy {
        baseOkHttpClient.newBuilder()
            .addInterceptor(headerInterceptor)
            .build()
    }

    private val converterFactories by lazy {
        listOf(MoshiConverterFactory.create())
    }

    private val retrofit by lazy {
        RetrofitWithFactories.create(okHttpClient, config.apiUrl, converterFactories)
    }

    private val valorantApi by lazy { retrofit.create(ValorantApi::class.java) }

    private val networkErrorHandler by lazy { NetworkErrorHandler() }

    val weaponsRemoteDataSource: WeaponsRemoteDataSource by lazy {
        WeaponsRemoteDataSourceImpl(
            valorantApi,
            networkErrorHandler
        )
    }
}
