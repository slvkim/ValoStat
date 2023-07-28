package com.mikyegresl.valostat.base.converter.responseToDto.news

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.model.news.ArticleCategoryDto
import com.mikyegresl.valostat.base.model.news.ArticleCategoryTypeDto
import com.mikyegresl.valostat.base.model.news.ArticleTagDto
import com.mikyegresl.valostat.base.model.news.ArticleTagTypeDto
import com.mikyegresl.valostat.base.model.news.ArticleTypeDto
import com.mikyegresl.valostat.base.model.news.AuthorDto
import com.mikyegresl.valostat.base.model.news.BannerContentTypeDto
import com.mikyegresl.valostat.base.model.news.BannerDimensionDto
import com.mikyegresl.valostat.base.model.news.BannerDto
import com.mikyegresl.valostat.base.network.model.news.ArticleAuthorResponse
import com.mikyegresl.valostat.base.network.model.news.ArticleBannerResponse
import com.mikyegresl.valostat.base.network.model.news.ArticleCategoryResponse
import com.mikyegresl.valostat.base.network.model.news.ArticleTagResponse
import com.mikyegresl.valostat.base.network.model.news.ArticleUrlResponse
import com.mikyegresl.valostat.base.utils.convertDate
import com.mikyegresl.valostat.base.utils.isYoutubeVideo

object ArticleRelatedResponseToDtoConverter {
    internal fun mapArticleType(articleType: String?, externalUrl: String?): ArticleTypeDto =
        when (articleType) {
            "Normal article" -> ArticleTypeDto.NORMAL_ARTICLE
            "External Link" -> {
                if (externalUrl.isYoutubeVideo()) ArticleTypeDto.YOUTUBE_VIDEO
                else ArticleTypeDto.EXTERNAL_LINK
            }
            else -> ArticleTypeDto.UNDEFINED
        }

    internal fun mapArticleUrl(from: ArticleUrlResponse?): String =
        from?.url?.let {
            if (it.startsWith("/")) {
                it.replaceFirst("/", "")
            } else it
        } ?: Converter.EMPTY_STRING

    internal fun mapDate(from: String?): String? =
        from?.convertDate()
}

object ArticleBannerResponseToDtoConverter : Converter<ArticleBannerResponse?, BannerDto?> {

    override fun convert(from: ArticleBannerResponse?): BannerDto? =
        from?.let {
            BannerDto(
                dimension = mapDimension(it.dimension),
                url = it.url ?: Converter.EMPTY_STRING,
                contentType = mapContentType(it.contentType)
            )
        }

    private fun mapDimension(from: ArticleBannerResponse.BannerDimensionResponse?): BannerDimensionDto =
        BannerDimensionDto(
            height = from?.height ?: 0,
            width = from?.width ?: 0
        )

    private fun mapContentType(from: String?): BannerContentTypeDto =
        when (from) {
            "image/jpeg" -> BannerContentTypeDto.IMAGE
            else -> BannerContentTypeDto.UNDEFINED
        }
}

object ArticleAuthorResponseToDtoConverter : Converter<ArticleAuthorResponse?, AuthorDto?> {

    override fun convert(from: ArticleAuthorResponse?): AuthorDto? =
        from?.let {
            AuthorDto(
                title = it.title ?: Converter.EMPTY_STRING,
                description = it.description ?: Converter.EMPTY_STRING,
                role = it.role ?: Converter.EMPTY_STRING,
                imgUrl = it.image?.url ?: Converter.EMPTY_STRING
            )
        }
}

object ArticleCategoryResponseToDtoConverter : Converter<ArticleCategoryResponse?, ArticleCategoryDto?> {

    override fun convert(from: ArticleCategoryResponse?): ArticleCategoryDto? =
        from?.let {
            ArticleCategoryDto(
                title = it.title ?: Converter.EMPTY_STRING,
                type = mapCategory(it.machineName),
                url = it.url?.url ?: Converter.EMPTY_STRING
            )
        }

    private fun mapCategory(from: String?): ArticleCategoryTypeDto =
        when (from) {
            "game_updates" -> ArticleCategoryTypeDto.GAME_UPDATES
            else -> ArticleCategoryTypeDto.UNDEFINED
        }
}

object ArticleTagResponseToDtoConverter : Converter<ArticleTagResponse?, ArticleTagDto> {

    override fun convert(from: ArticleTagResponse?): ArticleTagDto =
        ArticleTagDto(
            title = from?.title ?: Converter.EMPTY_STRING,
            type = mapType(from?.machineName),
            url = from?.url?.url ?: Converter.EMPTY_STRING,
            isHidden = from?.isHidden ?: true
        )

    private fun mapType(from: String?): ArticleTagTypeDto =
        when (from) {
            "patch_notes" -> ArticleTagTypeDto.PATCH_NOTES
            else -> ArticleTagTypeDto.UNDEFINED
        }
}