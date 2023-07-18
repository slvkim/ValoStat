package com.mikyegresl.valostat.base.model.news

data class ArticleDetailsDto(
    val id: String,
    val uid: String,
    val title: String,
    val description: String,
    val htmlContent: String,
    val date: String,
    val url: String,
    val externalLink: String,
    val type: ArticleTypeDto,
    val banner: BannerDto? = null,
    val author: AuthorDto? = null,
    val tags: List<ArticleTagDto>,
    val category: ArticleCategoryDto? = null
)