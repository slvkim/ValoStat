package com.mikyegresl.valostat.features.news

import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.news.ArticleDto
import com.mikyegresl.valostat.base.repository.NewsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class NewsViewModel(
    private val repository: NewsRepository,
) : BaseNavigationViewModel<NewsScreenState>() {

    companion object {
        private const val TAG = "NewsViewModel"
    }

    override val _state = MutableStateFlow<NewsScreenState>(
        NewsScreenState.NewsLoadingState
    )

    fun dispatchIntent(intent: NewsIntent) = when (intent) {
        is NewsIntent.UpdateNewsIntent -> {
            loadNews(intent.locale)
        }
    }

    private fun loadNews(locale: ValoStatLocale) = doBackground(
        repository.getNews(locale),
        onLoading = {
            _state.value = NewsScreenState.NewsLoadingState
        },
        onSuccessRemote = ::processSuccessfulLoad,
        onError = {
            _state.value = NewsScreenState.NewsErrorState(it)
            true
        }
    )

    private fun processSuccessfulLoad(news: List<ArticleDto>) {
        _state.value = NewsScreenState.NewsDataState(news)
    }
}