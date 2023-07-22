package com.mikyegresl.valostat.repository

import com.mikyegresl.valostat.base.converter.responseToDto.news.ArticleDetailsResponseToDtoConverter
import com.mikyegresl.valostat.base.converter.responseToDto.news.ArticleResponseToDtoConverter
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.news.ArticleDetailsDto
import com.mikyegresl.valostat.base.model.news.ArticleDto
import com.mikyegresl.valostat.base.network.Response
import com.mikyegresl.valostat.base.network.service.NewsRemoteDataSource
import com.mikyegresl.valostat.base.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource
) : NewsRepository {

    override fun getNews(locale: ValoStatLocale): Flow<Response<List<ArticleDto>>> = flow {
        coroutineScope {
            emit(Response.Loading())
            try {
                val remoteNews = remoteDataSource.getNews(lang = locale.title)
                val news = ArticleResponseToDtoConverter.convert(remoteNews)
                emit(Response.SuccessRemote(news))
            } catch (e: Exception) {
                emit(Response.Error<List<ArticleDto>>(e))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getNewsDetails(
        locale: ValoStatLocale,
        url: String
    ): Flow<Response<ArticleDetailsDto>> = flow {
        coroutineScope {
            emit(Response.Loading())
            try {
                val remoteNews = remoteDataSource.getNewsDetails(lang = locale.title, url)
                val news = ArticleDetailsResponseToDtoConverter.convert(remoteNews)
                emit(Response.SuccessRemote(news))
            } catch (e: Exception) {
                emit(Response.Error<ArticleDetailsDto>(e))
            }
        }
    }.flowOn(Dispatchers.IO)
}