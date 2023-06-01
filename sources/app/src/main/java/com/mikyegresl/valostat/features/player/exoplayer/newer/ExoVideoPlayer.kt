//package com.mikyegresl.valostat.features.player.exoplayer.newer
//
//import android.content.Context
//import android.net.Uri
//import android.os.Looper
//import android.util.Log
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.appcompat.content.res.AppCompatResources
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.view.isVisible
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleEventObserver
//import androidx.media3.common.util.Util
//import androidx.media3.datasource.DefaultDataSourceFactory
//import androidx.media3.datasource.cache.CacheDataSource
//import androidx.media3.datasource.cache.NoOpCacheEvictor
//import androidx.media3.datasource.cache.SimpleCache
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.exoplayer.source.MediaSource
//import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
//import androidx.media3.exoplayer.trackselection.TrackSelector
//import androidx.media3.extractor.DefaultExtractorsFactory
//import androidx.media3.extractor.ExtractorsFactory
//import androidx.media3.ui.PlayerView
//import com.mikyegresl.valostat.features.player.exoplayer.ExoPlayerFullScreenListener
//import java.io.File
//
//const val TAG = "PlayerVideoPlayer"
//
//abstract class ExoVideoPlayer(
//    private val mediaUri: String?,
//    private val fullScreenListener: ExoPlayerFullScreenListener
//) {
//
//    companion object {
//        private val VIDEO_EXTENSIONS = listOf( "avi", "mp4" )
//        private const val DOWNLOAD_CONTENT_DIRECTORY = "downloads"
//    }
//
//    private var dataSourceFactory: CacheDataSource.Factory? = null
//    private var extractorsFactory: ExtractorsFactory? = null
//
//    // player-related variables
//    private var exoPlayer: ExoPlayer? = null
//    private var trackSelector: TrackSelector? = null
//    private var playerView: PlayerView? = null
//
//    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
//    private fun initVideoPlaybackObjects(context: Context) {
//        val downloadContentDirectory = File(context.getExternalFilesDir(null), DOWNLOAD_CONTENT_DIRECTORY)
//        val downloadCache = SimpleCache(downloadContentDirectory, NoOpCacheEvictor())
//        val upstreamFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "ExoPlayer"))
//        dataSourceFactory = dataSourceFactory
//            ?: CacheDataSource.Factory()
//                .setCache(downloadCache)
//                .setUpstreamDataSourceFactory(upstreamFactory)
//        extractorsFactory = extractorsFactory ?: DefaultExtractorsFactory()
//    }
//
//    private fun initializeWithParams(mediaUri: String?, onPlayerReady: (ExoPlayer) -> Unit): Boolean {
//        return mediaUri?.let { sourceUrl ->
//            exoPlayer?.let {
//                loadVideoAndPreparePlayer(sourceUrl, onPlayerReady)
//                true
//            } ?: false
//        } ?: false
//    }
//
//    private fun loadVideoAndPreparePlayer(uri: String, onPlayerReady: (ExoPlayer) -> Unit) {
//        val extIdx = uri.lastIndexOf('.')
//        val ext = if (extIdx > 0) { uri.substring(extIdx + 1) } else { "" }
//        if (ext.isEmpty() || !VIDEO_EXTENSIONS.contains(ext)) {
////            Toast.makeText(playerView?.context, "This type of video may not be supported", Toast.LENGTH_LONG).show()
//            Log.e(TAG,"TODO: check if content by this link is really video but not HTML:"+uri)
//        }
//        val videoUri: Uri = Uri.parse(uri)
//        val videoSource: MediaSource.Factory = MediaSource(
//            videoUri,
//            dataSourceFactory,
//            extractorsFactory,
//            null, null
//        )
//        exoPlayer?.let {
//            onPlayerReady(it)
//            it.setMediaSource(videoSource)
//            it.prepare()
//        }
//    }
//
//    @Composable
//    fun InflatePlayer(modifier: Modifier) =
//        RenderPlayerView(
//            modifier = modifier,
//            playbackPosition = 0
//        )
//
//    @Composable
//    fun ContinuePlayback(
//        modifier: Modifier,
//        playbackPosition: Long
//    ) = RenderPlayerView(
//        modifier = modifier,
//        playbackPosition = playbackPosition
//    )
//
//    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
//    @Composable
//    private fun RenderPlayerView(
//        modifier: Modifier,
//        playbackPosition: Long
//    ) {
//        val lifecycleOwner = LocalLifecycleOwner.current
//
//        initVideoPlaybackObjects(LocalContext.current)
//        trackSelector = trackSelector ?: DefaultTrackSelector()
//
//        Box(modifier = modifier) {
//            DisposableEffect(
//                key1 = lifecycleOwner,
//                AndroidView(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .wrapContentHeight(),
//                    factory = { context ->
//                        PlayerView(context).let {
//                            setPlayerUi(context, it)
//                            initialize(playbackPosition)
//                            playerView = it
//                            it
//                        }
//                    }
//                )
//            ) {
//                val lifecycleObserver = LifecycleEventObserver { _, event ->
//                    onStateChanged(event, playbackPosition)
//                }
//                lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
//                onDispose {
//                    lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
//                    releaseResources()
//                }
//            }
//        }
//    }
//
//    private fun setPlayerUi(
//        context: Context,
//        playerView: PlayerView
//    ) {
//        val fullScreenBtn = playerView.findViewById<ImageView>(R.id.exo_fullscreen_icon)
//        fullScreenBtn.isVisible = true
//
//        fullScreenBtn.takeIf { it.isVisible }?.setOnClickListener {
//            val params = playerView.layoutParams
//
//            isFullScreen = if (isFullScreen) {
//                fullScreenListener.onExitFullScreen(
//                    exoPlayer?.currentPosition ?: 0,
//                    true
//                )
//                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
//                false
//            } else {
//                fullScreenListener.onEnterFullScreen(
//                    exoPlayer?.currentPosition ?: 0,
//                    true
//                )
//                params.height = ViewGroup.LayoutParams.MATCH_PARENT
//                true
//            }
//            params.width = ViewGroup.LayoutParams.MATCH_PARENT
//            playerView.layoutParams = params
//            setFullscreenImageByFullscreenState(context, playerView)
//        }
//        setFullscreenImageByFullscreenState(context, playerView)
//    }
//
//    private fun setFullscreenImageByFullscreenState(context: Context, playerView: PlayerView) {
//        val fullScreenBtn = playerView.findViewById<ImageView>(R.id.exo_fullscreen_icon) ?: return
//
//        fullScreenBtn.takeIf { it.isVisible }?.let {
//            if (fullScreenListener == null) return
//
//            val res = if (isFullScreen) R.drawable.ic_exit_fullscreen_white_18dp
//            else R.drawable.ic_fullscreen_white_18dp
//
//            fullScreenBtn.setImageDrawable(
//                AppCompatResources.getDrawable(context, res)
//            )
//        }
//    }
//
//    /**
//     * Lifecycle observer method that behaves according to state of lifecycle owner
//     */
//    private fun onStateChanged(event: Lifecycle.Event, playbackPosition: Long?) {
//        when (event) {
//            Lifecycle.Event.ON_STOP -> {
//                dispatchIntent(CleanupVideoPlayerIntent)
//            }
//
//            Lifecycle.Event.ON_START -> {
//                dispatchIntent(
//                    ReinitializeVideoPlayerIntent.ContinuePlaybackVideoPlayerIntent(
//                        playbackPosition = (playerState as? VideoPlayerReadyContentState)?.currentMillis
//                            ?: playbackPosition
//                            ?: 0
//                    )
//                )
//            }
//
//            else -> {}
//        }
//    }
//
//    private fun releaseResources() {
//        exoPlayer?.release()
//        exoPlayer = null
//    }
//
//    private fun initialize(playbackPosition: Long = 0) {
//        if (exoPlayer != null) {
//            return
//        }
//        if (playerView?.context == null) {
//            android.os.Handler(Looper.myLooper()!!).post { initialize(playbackPosition) }
//            Log.d(TAG, "ExoPlayer init scheduled")
//            return
//        }
//
//        exoPlayer = ExoPlayer.Builder(playerView?.context).build()
//        playerView?.player = exoPlayer
//
//        val isInitialized = params?.let { videoParams ->
//            initializeWithParams(videoParams) {
//                with(playbackPosition) { if (this > 0) it.seekTo(this) }
//            }
//        } ?: false
//        if (isInitialized) {
//            changeContentState(
//                VideoPlayerContentState.VideoPlayerReadyContentState(
//                    isEnded = false,
//                    isPlaying = false,
//                    currentMillis = config.millisOnInit
//                )
//            )
//        } else {
//            Log.e(ExoVideoPlayer::class.java.name, "Player has not been initialized")
//            // TODO based on flag show error on view if smth went wrong
//        }
//    }
//
//    private fun processOnReadyVideoViewClickIntent(state: VideoPlayerReadyContentState?) {
//        if (state == null) return
//        if (state.isPlaying) {
//            dispatchIntent(PauseCurrentVideo)
//        } else {
//            if (state.isEnded) {
//                dispatchIntent(RewindCurrentVideo())
//            } else {
//                dispatchIntent(PlayCurrentVideo)
//            }
//        }
//    }
//}