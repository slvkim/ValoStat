package com.mikyegresl.valostat.features.news.details

import com.mikyegresl.valostat.base.model.ValoStatLocale

sealed class NewsDetailsIntent {

    data class LoadNewsDetails(
        val locale: ValoStatLocale,
        val url: String
    ) : NewsDetailsIntent()
}
