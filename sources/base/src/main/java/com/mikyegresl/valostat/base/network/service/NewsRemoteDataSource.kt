package com.mikyegresl.valostat.base.network.service

import com.mikyegresl.valostat.base.network.model.news.NewsDetailsResponse
import com.mikyegresl.valostat.base.network.model.news.NewsResponse

interface NewsRemoteDataSource {

    suspend fun getNews(lang: String): NewsResponse

    suspend fun getNewsDetails(lang: String, url: String): NewsDetailsResponse

}