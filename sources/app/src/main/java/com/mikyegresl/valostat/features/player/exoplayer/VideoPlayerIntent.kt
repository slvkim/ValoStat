package com.mikyegresl.valostat.features.player.exoplayer

sealed class VideoPlayerIntent {

    object VideoViewGotClickedIntent : VideoPlayerIntent()

    sealed class VideoReadyPlayerIntent : VideoPlayerIntent() {
        object PlayCurrentVideo : VideoReadyPlayerIntent()
        object PauseCurrentVideo : VideoReadyPlayerIntent()
        class RewindCurrentVideo(
            val millisecond: Long = VIDEO_START_MILLISECOND_DEFAULT
        ) : VideoReadyPlayerIntent()
    }

    object CleanupVideoPlayerIntent: VideoPlayerIntent()

    sealed class ReinitializeVideoPlayerIntent: VideoPlayerIntent() {
        object StartFromBeginningVideoPlayerIntent: ReinitializeVideoPlayerIntent()

        data class ContinuePlaybackVideoPlayerIntent(
            val playbackPosition: Long
        ): ReinitializeVideoPlayerIntent()
    }

    companion object {
        const val VIDEO_START_MILLISECOND_DEFAULT = 0L
    }
}