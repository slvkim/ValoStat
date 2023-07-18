package com.mikyegresl.valostat.base.repository

import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.news.ArticleDetailsDto
import com.mikyegresl.valostat.base.model.news.ArticleDto
import com.mikyegresl.valostat.base.network.Response
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(locale: ValoStatLocale): Flow<Response<List<ArticleDto>>>

    fun getNewsDetails(locale: ValoStatLocale, url: String): Flow<Response<ArticleDetailsDto>>

}