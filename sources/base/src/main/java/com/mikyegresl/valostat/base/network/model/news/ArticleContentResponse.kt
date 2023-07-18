package com.mikyegresl.valostat.base.network.model.news

import com.google.gson.annotations.SerializedName

data class ArticleContentResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("uid")
    val uid: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("external_link")
    val externalLink: String? = null,
    @SerializedName("article_body")
    val articleBody: List<ArticleBodyResponse>? = null,
    @SerializedName("article_tags")
    val articleTags: List<ArticleTagResponse>? = null,
    @SerializedName("article_type")
    val articleType: String? = null,
    @SerializedName("author")
    val authors: List<ArticleAuthorResponse>? = null,
    @SerializedName("banner")
    val banner: ArticleBannerResponse? = null,
//    @SerializedName("banner_settings")
//    val banner_settings: BannerSettings,
    @SerializedName("category")
    val category: List<ArticleCategoryResponse>? = null,
    @SerializedName("url")
    val url: ArticleUrlResponse? = null
) {
    data class ArticleBodyResponse(
        @SerializedName("rich_text_editor")
        val richTextEditor: ArticleRichTextResponse? = null,
    ) {
        data class ArticleRichTextResponse(
            @SerializedName("rich_text_editor")
            val richText: String? = null
        )
    }
}