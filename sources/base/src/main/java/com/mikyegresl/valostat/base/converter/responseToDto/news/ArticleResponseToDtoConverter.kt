package com.mikyegresl.valostat.base.converter.responseToDto.news

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.model.news.ArticleDto
import com.mikyegresl.valostat.base.network.model.news.NewsResponse

object ArticleResponseToDtoConverter : Converter<NewsResponse, List<ArticleDto>> {

    override fun convert(from: NewsResponse): List<ArticleDto> {
        val result = mutableListOf<ArticleDto>()

        from.result?.data?.articles?.articles?.forEach {
            result.add(
                ArticleDto(
                    id = it.id ?: Converter.EMPTY_STRING,
                    uid = it.uid ?: Converter.EMPTY_STRING,
                    title = it.title ?: Converter.EMPTY_STRING,
                    description = it.description ?: Converter.EMPTY_STRING,
                    date = ArticleRelatedResponseToDtoConverter.mapDate(it.date) ?: Converter.EMPTY_STRING,
                    url = ArticleRelatedResponseToDtoConverter.mapArticleUrl(it.url),
                    externalLink = it.externalLink ?: Converter.EMPTY_STRING,
                    type = ArticleRelatedResponseToDtoConverter.mapArticleType(it.type, it.externalLink),
                    banner = ArticleBannerResponseToDtoConverter.convert(it.banner),
                )
            )
        }
        return result
    }
}