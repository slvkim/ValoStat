package com.mikyegresl.valostat.features.news

import com.mikyegresl.valostat.base.model.news.ArticleDto
import com.mikyegresl.valostat.common.state.BaseState

sealed class NewsScreenState: BaseState {

    object NewsLoadingState : NewsScreenState()

    class NewsErrorState(val t: Throwable) : NewsScreenState()

    data class NewsDataState(
        val newsList: List<ArticleDto> = emptyList()
    ) : NewsScreenState()
}