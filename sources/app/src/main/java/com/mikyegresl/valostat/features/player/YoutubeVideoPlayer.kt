package com.mikyegresl.valostat.features.player

import android.os.Looper
import android.util.Log
import android.view.View
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class YoutubeVideoPlayer(
    private val videoUrl: String,
    private val lifecycleOwner: LifecycleOwner,
    private val onYoutubeEnterFullscreen: () -> Unit,
    private val onYoutubeExitFullscreen: () -> Unit
) : LifecycleEventObserver {

    companion object {
        private const val TAG = "YoutubeVideoPlayer"
        private const val PLAYER_INIT_REPEAT_DELAY_MS = 100L
        private const val JS_READY_DELAY_MS = .5f
        private val youtubeRegex = Regex("^((http|https):\\/\\/)?(((www|m)\\.)?youtube\\.com|youtu\\.?be)\\/((watch\\?v=)?([a-zA-Z0-9\\-_]{11}))(&.*)*\$")
    }

    private var youtubePlayerView: YouTubePlayerView? = null
    private var youtubePlayer: YouTubePlayer? = null
    private var listener: AbstractYouTubePlayerListener? = null

    private var isFullscreen = false

    @Composable
    fun InflatePlayerView(
        modifier: Modifier
    ) {
        DisposableEffect(
            AndroidView(
                modifier = modifier,
                factory = { context ->
                    YouTubePlayerView(context).let {
                        lifecycleOwner.lifecycle.addObserver(this@YoutubeVideoPlayer)
                        youtubePlayerView = it
                        initialize()
                        it
                    }
                }
            )
        ) {
            onDispose { releaseResources() }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {}
            Lifecycle.Event.ON_PAUSE -> {}
            Lifecycle.Event.ON_START -> {}
            Lifecycle.Event.ON_DESTROY -> {}
            else -> {}
        }
    }

    private fun releaseResources() {
        youtubePlayer?.let {
            youtubePlayerView?.release()
            youtubePlayerView = null
        }
        listener?.let { youtubePlayer?.removeListener(it) }
        (youtubePlayer as? WebView)?.destroy()
        youtubePlayer = null
    }

    private fun initialize() {
        if (youtubePlayer != null) {
            Log.d(TAG, "Youtube Player Aready initialized")
            return
        }
        if (youtubePlayerView == null) {
            Log.d(TAG, "Youtube Player init: context is not available yet")
            android.os.Handler(Looper.myLooper()!!).postDelayed({ initialize() }, PLAYER_INIT_REPEAT_DELAY_MS)
            Log.d(TAG, "YouTube Player init scheduled")
            return
        }
        setUpPlayer(youtubePlayerView!!, onReady = {
            Log.d(TAG, "Youtube Player init: OK!")
            youtubePlayer = it
//            generateVideoId(videoUrl)?.let { videoId ->
//                Log.e(TAG, "initialize(): videoId = $videoId")
                it.loadVideo(videoUrl, JS_READY_DELAY_MS)
//            }
        })
    }

    private fun setUpPlayer(it: YouTubePlayerView, onReady: (YouTubePlayer) -> Unit) {
        listener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                onReady.invoke(youTubePlayer)
            }
        }
        val iFramePlayerOptions: IFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1)
            .build()
        it.enableAutomaticInitialization = false
        it.initialize(listener!!, iFramePlayerOptions)
        it.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                Log.e(TAG, "viewId: ${fullscreenView.id}, hash: ${fullscreenView.hashCode()}")
                onYoutubeEnterFullscreen()
                Log.e(TAG, "viewId: ${fullscreenView.id}, hash: ${fullscreenView.hashCode()}")
                youtubePlayer?.toggleFullscreen()
            }

            override fun onExitFullscreen() {
                onYoutubeExitFullscreen()
                youtubePlayer?.toggleFullscreen()
            }
        })
    }

    private fun generateVideoId(url: String) =
        (youtubeRegex.matchEntire(url)?.groups as? MatchNamedGroupCollection)?.get(8)?.value
}