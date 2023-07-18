package com.mikyegresl.valostat.base.converter.responseToDto.news

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.model.news.ArticleDetailsDto
import com.mikyegresl.valostat.base.network.model.news.NewsDetailsResponse

object ArticleDetailsResponseToDtoConverter : Converter<NewsDetailsResponse?, ArticleDetailsDto> {

    override fun convert(from: NewsDetailsResponse?): ArticleDetailsDto =
        with(from?.result?.data?.articleContent) {
            ArticleDetailsDto(
                id = this?.id ?: Converter.EMPTY_STRING,
                uid = this?.uid?: Converter.EMPTY_STRING,
                title = this?.title ?: Converter.EMPTY_STRING,
                description = this?.description ?: Converter.EMPTY_STRING,
                htmlContent = this?.articleBody?.firstOrNull()?.richTextEditor?.richText ?: Converter.EMPTY_STRING,
                date = ArticleRelatedResponseToDtoConverter.mapDate(this?.date) ?: Converter.EMPTY_STRING,
                url = ArticleRelatedResponseToDtoConverter.mapArticleUrl(this?.url),
                externalLink = this?.externalLink ?: Converter.EMPTY_STRING,
                type = ArticleRelatedResponseToDtoConverter.mapArticleType(this?.articleType),
                banner = ArticleBannerResponseToDtoConverter.convert(this?.banner),
                author = ArticleAuthorResponseToDtoConverter.convert(this?.authors?.firstOrNull()),
                tags = this?.articleTags?.map { tag -> ArticleTagResponseToDtoConverter.convert(tag) } ?: emptyList(),
                category = ArticleCategoryResponseToDtoConverter.convert(this?.category?.firstOrNull())
            )
        }
}