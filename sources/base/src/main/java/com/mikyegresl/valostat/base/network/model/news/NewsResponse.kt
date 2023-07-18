package com.mikyegresl.valostat.base.network.model.news

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("result")
    val result: NewsResultResponse? = null
) {
    data class NewsResultResponse(
        @SerializedName("data")
        val data: NewsDataResponse? = null
    ) {
        data class NewsDataResponse(
            @SerializedName("allContentstackArticles")
            val articles: NewsArticleStackResponse? = null
        ) {
            data class NewsArticleStackResponse(
                @SerializedName("nodes")
                val articles: List<NewsArticleResponse>? = null
            )

            data class NewsArticleResponse(
                @SerializedName("id")
                val id: String? = null,
                @SerializedName("uid")
                val uid: String? = null,
                @SerializedName("title")
                val title: String? = null,
                //"date":"2023-07-14T14:00:00.000Z",
                @SerializedName("date")
                val date: String? = null,
                @SerializedName("description")
                val description: String? = null,
                @SerializedName("article_type")
                val type: String? = null,
                @SerializedName("external_link")
                val externalLink: String? = null,
                @SerializedName("url")
                val url: ArticleUrlResponse? = null,
                @SerializedName("banner")
                val banner: ArticleBannerResponse? = null
            )
        }
    }
}
