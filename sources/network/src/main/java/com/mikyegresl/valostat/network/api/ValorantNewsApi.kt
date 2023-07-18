package com.mikyegresl.valostat.network.api

import com.mikyegresl.valostat.base.network.model.news.NewsDetailsResponse
import com.mikyegresl.valostat.base.network.model.news.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ValorantNewsApi {

    @GET("page-data/{lang}/news/page-data.json")
    suspend fun getNews(@Path("lang") language: String): NewsResponse

    @GET("page-data/{lang}/news/{url}/page-data.json")
    suspend fun getNewsDetails(@Path("lang") language: String, @Path("url") articleUrl: String): NewsDetailsResponse
}