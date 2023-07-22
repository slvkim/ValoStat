package com.mikyegresl.valostat.features.news.details

import com.mikyegresl.valostat.base.model.news.ArticleDetailsDto
import com.mikyegresl.valostat.common.state.BaseState

sealed class NewsDetailsScreenState : BaseState {

    object NewsDetailsLoadingState : NewsDetailsScreenState()

    class NewsDetailsErrorState(val t: Throwable) : NewsDetailsScreenState()

    data class NewsDetailsDataState(
        val details: ArticleDetailsDto
    ) : NewsDetailsScreenState()
}
