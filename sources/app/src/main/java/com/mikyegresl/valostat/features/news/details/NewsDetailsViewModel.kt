package com.mikyegresl.valostat.features.news.details

import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.news.ArticleDetailsDto
import com.mikyegresl.valostat.base.model.news.ArticleDto
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

    fun dispatchIntent(intent: NewsDetailsIntent) {
        when (intent) {
            is NewsDetailsIntent.LoadNewsDetails -> {
                loadNews(intent.locale, intent.url)
            }
            is NewsDetailsIntent.ProcessYoutubeVideo -> {
                processExternalLink(intent.singleArticle)
            }
            is NewsDetailsIntent.EmitNotSupportedYet -> {
                emitErrorState(Throwable("This format is not supported yet"))
            }
        }
    }

    private fun loadNews(locale: ValoStatLocale, url: String) = doBackground(
        repository.getNewsDetails(locale, url),
        onLoading = {
            _state.value = NewsDetailsScreenState.NewsDetailsLoadingState
        },
        onSuccessRemote = ::processSuccessfulLoad,
        onError = {
            emitErrorState(it)
            true
        }
    )

    private fun processSuccessfulLoad(details: ArticleDetailsDto) {
        _state.value = NewsDetailsScreenState.NewsDetailsDataState(details)
    }

    private fun processExternalLink(singleArticle: ArticleDto) = with(singleArticle) {
        _state.value = NewsDetailsScreenState.NewsDetailsDataState(
            ArticleDetailsDto(
                id = id,
                uid = uid,
                title = title,
                description = description,
                htmlContent = "",
                date = date,
                url = url,
                externalLink = externalLink,
                type = type,
                banner = banner,
                author = null,
                tags = emptyList(),
                category = null,
            )
        )
    }

    private fun emitErrorState(throwable: Throwable) {
        _state.value = NewsDetailsScreenState.NewsDetailsErrorState(throwable)
    }
}