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
    private val activity: Activity,
    private val beforeEnterFullScreen: (Long, Boolean) -> Boolean = { _, _ -> true },
    private val beforeExitFullScreen: (Long, Boolean) -> Boolean = { _, _ -> true },
) : ExoPlayerFullScreenListener {

    override fun onEnterFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean) {
        if (beforeEnterFullScreen(currentPlaybackPosition, playOnInit)) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onExitFullScreen(currentPlaybackPosition: Long, playOnInit: Boolean) {
        if (beforeExitFullScreen(currentPlaybackPosition, playOnInit)) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}