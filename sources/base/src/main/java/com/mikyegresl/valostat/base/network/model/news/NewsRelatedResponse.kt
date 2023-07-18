package com.mikyegresl.valostat.base.network.model.news

import com.google.gson.annotations.SerializedName

data class ArticleBannerResponse(
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("dimension")
    val dimension: BannerDimensionResponse? = null,
    @SerializedName("content_type")
    val contentType: String? = null
) {
    data class BannerDimensionResponse(
        @SerializedName("height")
        val height: Int? = null,
        @SerializedName("width")
        val width: Int? = null
    )
}

data class ArticleUrlResponse(
    @SerializedName("url")
    val url: String? = null
)

data class ArticleCategoryResponse(
    @SerializedName("machine_name")
    val machineName: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("url")
    val url: ArticleUrlResponse? = null
)

data class ArticleTagResponse(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("url")
    val url: ArticleUrlResponse? = null,
    @SerializedName("machine_name")
    val machineName: String? = null,
    @SerializedName("isHidden")
    val isHidden: Boolean? = null
)

data class ArticleAuthorResponse(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("image")
    val image: ArticleUrlResponse? = null
)