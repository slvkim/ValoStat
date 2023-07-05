package com.mikyegresl.valostat.features.player.exoplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo

interface ExoPlayerFullScreenListener {

    fun onEnterFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean)

    fun onExitFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean)
}

class SimpleExoPlayerFullScreenListenerImpl(
    private val activity: Activity,
) : ExoPlayerFullScreenListener {

    override fun onEnterFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onExitFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}

class PreExecExoPlayerFullScreenListenerImpl(
    private val enterFullscreenMode: (Long, Boolean) -> Unit,
    private val exitFullscreenMode: (Long, Boolean) -> Unit
) : ExoPlayerFullScreenListener {

    override fun onEnterFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean) {
        enterFullscreenMode(currentPlaybackPosition, playOnInit)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onExitFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean) {
        exitFullscreenMode(currentPlaybackPosition, playOnInit)
    }
}