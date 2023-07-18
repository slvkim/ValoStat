package com.mikyegresl.valostat.features.news

import com.mikyegresl.valostat.base.model.ValoStatLocale

sealed class NewsIntent {

    data class UpdateNewsIntent(
        val locale: ValoStatLocale
    ) : NewsIntent()
}