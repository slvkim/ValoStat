package com.mikyegresl.valostat.network

import com.mikyegresl.valostat.base.network.config.NetworkConfig
import com.mikyegresl.valostat.base.network.service.AgentsRemoteDataSource
import com.mikyegresl.valostat.base.network.service.NewsRemoteDataSource
import com.mikyegresl.valostat.base.network.service.VideosRemoteDataSource
import com.mikyegresl.valostat.base.network.service.WeaponsRemoteDataSource
import com.mikyegresl.valostat.network.api.ValorantApi
import com.mikyegresl.valostat.network.api.ValorantNewsApi
import com.mikyegresl.valostat.network.api.YoutubeDataApi
import com.mikyegresl.valostat.network.error.NetworkErrorHandler
import com.mikyegresl.valostat.network.interceptor.AuthInterceptor
import com.mikyegresl.valostat.network.interceptor.HeaderInterceptor
import com.mikyegresl.valostat.network.service.AgentsRemoteDataSourceImpl
import com.mikyegresl.valostat.network.service.NewsRemoteDataSourceImpl
import com.mikyegresl.valostat.network.service.VideosRemoteDataSourceImpl
import com.mikyegresl.valostat.network.service.WeaponsRemoteDataSourceImpl
import com.mikyegresl.valostat.network.util.OkHttpClientBuilder
import com.mikyegresl.valostat.network.util.RetrofitWithFactories
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

class NetworkFactory(
    private val config: NetworkConfig
) {

    private val headerInterceptor by lazy { HeaderInterceptor() }

    private val authInterceptor by lazy { AuthInterceptor() }

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
            .addInterceptor(authInterceptor)
            .build()
    }

    private val converterFactories by lazy {
        listOf(GsonConverterFactory.create())
    }

    private val valorantApi by lazy {
        RetrofitWithFactories.create(
            okHttpClient,
            config.apiUrl,
            converterFactories
        ).create(ValorantApi::class.java)
    }

    private val newsApi by lazy {
        RetrofitWithFactories.create(
            okHttpClient,
            config.newsApiUrl,
            converterFactories
        ).create(ValorantNewsApi::class.java)
    }

    private val youtubeDataApi by lazy {
        RetrofitWithFactories.create(
            okHttpClient,
            config.videoApiUrl,
            converterFactories
        ).create(YoutubeDataApi::class.java)
    }

    private val networkErrorHandler by lazy { NetworkErrorHandler() }

    val agentsRemoteDataSource: AgentsRemoteDataSource by lazy {
        AgentsRemoteDataSourceImpl(
            valorantApi,
            networkErrorHandler
        )
    }

    val weaponsRemoteDataSource: WeaponsRemoteDataSource by lazy {
        WeaponsRemoteDataSourceImpl(
            valorantApi,
            networkErrorHandler
        )
    }

    val videosRemoteDataSource: VideosRemoteDataSource by lazy {
        VideosRemoteDataSourceImpl(
            youtubeDataApi,
            networkErrorHandler
        )
    }

    val newsRemoteDataSource: NewsRemoteDataSource by lazy {
        NewsRemoteDataSourceImpl(
            newsApi,
            networkErrorHandler
        )
    }
}
