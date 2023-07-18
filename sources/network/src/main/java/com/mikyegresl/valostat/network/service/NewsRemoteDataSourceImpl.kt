package com.mikyegresl.valostat.network.service

import com.mikyegresl.valostat.base.error.ErrorHandler
import com.mikyegresl.valostat.base.network.model.news.NewsDetailsResponse
import com.mikyegresl.valostat.base.network.model.news.NewsResponse
import com.mikyegresl.valostat.base.network.service.NewsRemoteDataSource
import com.mikyegresl.valostat.network.api.ValorantNewsApi

class NewsRemoteDataSourceImpl(
    private val api: ValorantNewsApi,
    private val errorHandler: ErrorHandler
): NewsRemoteDataSource {

    override suspend fun getNews(lang: String): NewsResponse =
        errorHandler.handleError { api.getNews(lang) }

    override suspend fun getNewsDetails(lang: String, url: String): NewsDetailsResponse =
        errorHandler.handleError { api.getNewsDetails(lang, url) }
}