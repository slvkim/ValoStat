package com.mikyegresl.valostat.base.model.news

data class BannerDto(
    val dimension: BannerDimensionDto,
    val url: String,
    val contentType: BannerContentTypeDto
)

enum class BannerContentTypeDto {
    IMAGE,
    UNDEFINED
}

data class BannerDimensionDto(
    val height: Int,
    val width: Int
)

data class AuthorDto(
    val title: String,
    val description: String,
    val role: String,
    val imgUrl: String
)

data class ArticleTagDto(
    val title: String,
    val type: ArticleTagTypeDto,
    val url: String,
    val isHidden: Boolean
)

data class ArticleCategoryDto(
    val title: String,
    val type: ArticleCategoryTypeDto,
    val url: String
)

enum class ArticleCategoryTypeDto {
    UNDEFINED,
    GAME_UPDATES
}

enum class ArticleTagTypeDto {
    UNDEFINED,
    PATCH_NOTES
}