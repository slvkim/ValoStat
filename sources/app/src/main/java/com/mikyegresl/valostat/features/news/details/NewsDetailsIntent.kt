package com.mikyegresl.valostat.features.news.details

import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.news.ArticleDto

sealed class NewsDetailsIntent {

    data class LoadNewsDetails(
        val locale: ValoStatLocale,
        val url: String
    ) : NewsDetailsIntent()

    object EmitNotSupportedYet: NewsDetailsIntent()

    data class ProcessYoutubeVideo(
        val singleArticle: ArticleDto
    ): NewsDetailsIntent()
}
