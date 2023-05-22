package com.mikyegresl.valostat.features.video

import com.mikyegresl.valostat.base.repository.VideosRepository
import com.mikyegresl.valostat.common.viewmodel.BaseNavigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class VideosViewModel
    (private val repository: VideosRepository
) : BaseNavigationViewModel<VideosScreenState>() {

    companion object {
        private const val TAG = "VideosViewModel"
    }

    override val _state = MutableStateFlow<VideosScreenState>(
        VideosScreenState.VideosScreenLoadingState
    )

    init {
        loadChannelOverview()
    }

    fun dispatchIntent(intent: VideosScreenIntent) {
        when (intent) {
            VideosScreenIntent.LoadNextVideosIntent -> {
                val dataState = currentState as VideosScreenState.VideosScreenDataState
                loadVideos(dataState.channelOverview.channelId)
            }
        }
    }

    private fun loadChannelOverview() =
        doBackground(
            repository.getChannelOverview(),
            onLoading = {
                updateState(VideosScreenState.VideosScreenLoadingState)
            },
            onSuccessRemote = { overview ->
                updateState(VideosScreenState.VideosScreenDataState(channelOverview = overview))
            },
            onError = {
                updateState(VideosScreenState.VideosScreenErrorState(it))
                true
            }
        ).invokeOnCompletion {
            loadVideos(
                (currentState as VideosScreenState.VideosScreenDataState).channelOverview.channelId
            )
        }

    private fun loadVideos(channelId: String) {
        val dataState = (currentState as VideosScreenState.VideosScreenDataState)

        doBackground(
            repository.getVideosFromChannel(channelId),
            onLoading = {
//                updateState(VideosScreenState.VideosScreenLoadingState)
            },
            onSuccessRemote = {
                updateState(
                    dataState.copy(
                        videos = it.videos,
                        videoPageInfo = it.pageInfo
                    )
                )
            },
            onError = {
                updateState(VideosScreenState.VideosScreenErrorState(it))
                true
            }
        ).invokeOnCompletion {
            loadVideoStats()
        }
    }

    private fun loadVideoStats() {
        val dataState = currentState as VideosScreenState.VideosScreenDataState
        val ids = dataState.videos.map { it.videoId }
        doBackground(
            repository.loadVideoStats(ids),
            onSuccessRemote = {
                updateState(dataState.copy(stats = it))
            }
        )
    }
}