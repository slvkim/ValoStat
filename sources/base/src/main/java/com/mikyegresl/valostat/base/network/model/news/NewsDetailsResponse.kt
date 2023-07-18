package com.mikyegresl.valostat.base.network.model.news

import com.google.gson.annotations.SerializedName

data class NewsDetailsResponse(
    @SerializedName("result")
    val result: NewsDetailsResultResponse,
) {
    data class NewsDetailsResultResponse(
        @SerializedName("data")
        val data: NewsDetailsDataResponse,
        @SerializedName("pageContext")
        val pageContext: PageContext
    ) {
        data class NewsDetailsDataResponse(
            @SerializedName("contentstackArticles")
            val articleContent: ArticleContentResponse,
        )
    }
}