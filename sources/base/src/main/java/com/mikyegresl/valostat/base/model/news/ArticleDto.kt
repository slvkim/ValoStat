package com.mikyegresl.valostat.base.model.news

data class ArticleDto(
    val id: String,
    val uid: String,
    val title: String,
    val description: String,
    val date: String,
    val url: String,
    val externalLink: String,
    val type: ArticleTypeDto,
    val banner: BannerDto? = null
)