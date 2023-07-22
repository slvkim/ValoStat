package com.mikyegresl.valostat.features.news.details

import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.news.ArticleDetailsDto
import com.mikyegresl.valostat.base.repository.NewsRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class NewsDetailsViewModel(
    private val repository: NewsRepository,
) : BaseNavigationViewModel<NewsDetailsScreenState>() {

    companion object {
        private const val TAG = "NewsViewModel"
    }

    override val _state = MutableStateFlow<NewsDetailsScreenState>(
        NewsDetailsScreenState.NewsDetailsLoadingState
    )

    fun dispatchIntent(intent: NewsDetailsIntent) = when (intent) {
        is NewsDetailsIntent.LoadNewsDetails -> {
            loadNews(intent.locale, intent.url)
        }
    }

    private fun loadNews(locale: ValoStatLocale, url: String) = doBackground(
        repository.getNewsDetails(locale, url),
        onLoading = {
            _state.value = NewsDetailsScreenState.NewsDetailsLoadingState
        },
        onSuccessRemote = ::processSuccessfulLoad,
        onError = {
            _state.value = NewsDetailsScreenState.NewsDetailsErrorState(it)
            true
        }
    )

    private fun processSuccessfulLoad(details: ArticleDetailsDto) {
        _state.value = NewsDetailsScreenState.NewsDetailsDataState(details)
    }
}