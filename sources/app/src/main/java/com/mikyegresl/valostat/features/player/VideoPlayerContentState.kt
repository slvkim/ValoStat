package com.mikyegresl.valostat.features.player

/**
 * State represents current mode of playable content
 */
sealed class VideoPlayerContentState {

    object VideoPlayerLoadingContentState : VideoPlayerContentState()

    data class VideoPlayerReadyContentState(
        val isEnded: Boolean,
        val isPlaying: Boolean,
        val isLoadingVideo: Boolean = false,
        val currentMillis: Long = VideoPlayerIntent.VIDEO_START_MILLISECOND_DEFAULT
    ) : VideoPlayerContentState()
}
