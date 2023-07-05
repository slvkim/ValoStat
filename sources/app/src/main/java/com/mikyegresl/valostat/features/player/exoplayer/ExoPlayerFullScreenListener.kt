package com.mikyegresl.valostat.features.player.exoplayer

import android.annotation.SuppressLint

interface ExoPlayerFullScreenListener {

    fun onEnterFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean)

    fun onExitFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean)
}

class PreExecExoPlayerFullScreenListenerImpl(
    private val beforeEnterFullScreen: (Long, Boolean) -> Boolean = { _, _ -> true },
    private val beforeExitFullScreen: (Long, Boolean) -> Boolean = { _, _ -> true },
    private val enterFullscreenMode: () -> Unit = {},
    private val exitFullscreenMode: () -> Unit = {}
) : ExoPlayerFullScreenListener {

    override fun onEnterFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean) {
        if (beforeEnterFullScreen(currentPlaybackPosition, playOnInit)) {
            enterFullscreenMode()
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onExitFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean) {
        if (beforeExitFullScreen(currentPlaybackPosition, playOnInit)) {
            exitFullscreenMode()
        }
    }
}