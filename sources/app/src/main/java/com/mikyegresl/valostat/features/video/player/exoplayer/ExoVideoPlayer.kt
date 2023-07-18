package com.mikyegresl.valostat.features.video.player.exoplayer

import android.content.Context
import android.media.session.PlaybackState
import android.net.Uri
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import com.mikyegresl.valostat.features.video.player.VideoPlayerContentState
import com.mikyegresl.valostat.features.video.player.VideoPlayerIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class ExoVideoPlayer(
    private val mediaUrl: String,
    private val lifecycleOwner: LifecycleOwner,
    private val uiCoroutineScope: CoroutineScope,
    private val onEnterFullscreen: (Long, Boolean) -> Unit,
    private val onExitFullscreen: (Long, Boolean) -> Unit,
    private val config: ExoPlayerConfig
) {
    private var isFullScreen = config.areInFullscreenFromStart

    companion object {
        const val TAG = "ExoBaseMediaPlayer"
        private const val DOWNLOAD_CONTENT_DIRECTORY = "downloads"
        private const val ALLOWED_CACHE_SIZE: Long = 90 * 1024 * 1024

        private val cacheEvictor by lazy {
            LeastRecentlyUsedCacheEvictor(ALLOWED_CACHE_SIZE)
        }

        private var simpleCache: Cache? = null

        private var databaseProvider: StandaloneDatabaseProvider? = null
        private var dataSourceFactory: CacheDataSource.Factory? = null
        private var upstreamFactory: DataSource.Factory? = null
    }

    private var exoPlayer: ExoPlayer? = null
    private var playerView: PlayerView? = null

    private var dispatchedPlayOnInit = false

    private val _playerStateFlow = lazy { MutableStateFlow<VideoPlayerContentState>(
        VideoPlayerContentState.VideoPlayerLoadingContentState
    ) }
    val state get() = _playerStateFlow.value.asStateFlow()
    private val playerState: VideoPlayerContentState get() = state.value

    private fun initializeWithParams(mediaUrl: String): Boolean {
        return exoPlayer?.let {
            loadVideoAndPreparePlayer(mediaUrl)
            true
        } ?: false
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @Composable
    fun RenderPlayerView(
        modifier: Modifier
    ) {
        initVideoPlaybackObjects(LocalContext.current)

        val state = _playerStateFlow.value.collectAsStateWithLifecycle()
        val dispatchedState = remember { mutableStateOf<VideoPlayerContentState?>(null) }

        key(state) {
            (state.value as? VideoPlayerContentState.VideoPlayerReadyContentState)?.let {
                if (dispatchedState.value != it) {
                    reactOnVideoReadyStateOnPlayer(it)
                    dispatchedState.value = it
                }
            }
        }
        Box(modifier = modifier) {
            DisposableEffect(
                key1 = lifecycleOwner,
                AndroidView(
                    factory = { context ->
                        PlayerView(context).let {
                            setPlayerUi(context, it)
                            initialize(config.playbackPosition)
                            it.keepScreenOn = config.keepScreenOnWhenPlayerInitialized
                            playerView = it
                            it
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            ) {
                val lifecycleObserver = LifecycleEventObserver { _, event ->
                    onStateChanged(event, config.playbackPosition)
                }
                lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
                    releaseResources()
                }
            }
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun setPlayerUi(
        context: Context,
        playerView: PlayerView
    ) {
        playerView.controllerAutoShow = false
        playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)

        val layoutParams = if (isFullScreen) {
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        } else {
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        playerView.layoutParams = layoutParams

        val prevBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_prev)
        val nextBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_next)
        val repeatToggleBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_repeat_toggle)
        val subtitleBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_subtitle)
        val overflowBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_overflow_hide)
        val settingsBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_settings)

        prevBtn.visibility = View.GONE
        nextBtn.visibility = View.GONE
        repeatToggleBtn.visibility = View.GONE
        subtitleBtn.visibility = View.GONE
        overflowBtn.visibility = View.GONE
        settingsBtn.visibility = View.VISIBLE

        val fullScreenBtn = playerView.findViewById<ImageView>(androidx.media3.ui.R.id.exo_fullscreen)
        fullScreenBtn.visibility = View.VISIBLE

        fullScreenBtn.takeIf { it.isVisible }?.setOnClickListener {
            val params = playerView.layoutParams
            val playOnInit = exoPlayer?.isPlaying ?: false
            val playbackPosition = exoPlayer?.currentPosition ?: 0

            isFullScreen = if (isFullScreen) {
                onExitFullscreen(playbackPosition, playOnInit)
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                false
            } else {
                onEnterFullscreen(playbackPosition, playOnInit)
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                true
            }
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            playerView.layoutParams = params
            setFullscreenImageByFullscreenState(context, playerView)
        }
        setFullscreenImageByFullscreenState(context, playerView)
    }

    private fun setFullscreenImageByFullscreenState(context: Context, playerView: PlayerView) {
        val fullScreenBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_fullscreen) ?: return

        val res = if (isFullScreen) com.mikyegresl.valostat.R.drawable.ic_disable_fullscreen
        else com.mikyegresl.valostat.R.drawable.ic_enable_fullscreen

        fullScreenBtn.setImageDrawable(
            AppCompatResources.getDrawable(context, res)
        )
    }

    private fun initialize(playbackPosition: Long = 0) {
        if (exoPlayer != null) {
            return
        }
        val context = playerView?.context
        if (context == null) {
            android.os.Handler(Looper.myLooper()!!).post { initialize(playbackPosition) }
            Log.d(TAG, "ExoPlayer init scheduled")
            return
        }

        exoPlayer = ExoPlayer.Builder(context).build()
        playerView?.player = exoPlayer
        setUpPlayer(exoPlayer!!)

        val isInitialized = initializeWithParams(mediaUrl)
        if (isInitialized) {
            changeContentState(
                VideoPlayerContentState.VideoPlayerReadyContentState(
                    isEnded = false,
                    isPlaying = false,
                    currentMillis = config.playbackPosition
                )
            )
        } else {
            Log.e(ExoVideoPlayer::class.java.name, "Player has not been initialized")
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun loadVideoAndPreparePlayer(uri: String) {
        val videoSource = DefaultMediaSourceFactory(dataSourceFactory!!)
            .createMediaSource(MediaItem.fromUri(Uri.parse(uri)))
        exoPlayer?.let {
            it.setMediaSource(videoSource, config.playbackPosition)
            it.prepare()
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initVideoPlaybackObjects(context: Context) {
        val downloadContentDirectory = File(context.getExternalFilesDir(null), DOWNLOAD_CONTENT_DIRECTORY)

        upstreamFactory = upstreamFactory ?: DefaultDataSource.Factory(context)
        databaseProvider = databaseProvider ?: StandaloneDatabaseProvider(context)
        simpleCache = simpleCache ?: SimpleCache(
            downloadContentDirectory,
            cacheEvictor,
            databaseProvider!!
        )
        dataSourceFactory = dataSourceFactory ?: CacheDataSource.Factory()
            .setCache(simpleCache!!)
            .setUpstreamDataSourceFactory(upstreamFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    private fun reactOnVideoReadyStateOnPlayer(
        state: VideoPlayerContentState.VideoPlayerReadyContentState,
    ) {
        exoPlayer ?: return

        val needToStartPlay = (state.isPlaying && exoPlayer?.playbackState != PlaybackState.STATE_PLAYING)
                || config.playOnInit && !dispatchedPlayOnInit

        if (needToStartPlay) {
            exoPlayer?.playWhenReady = true
            dispatchedPlayOnInit = true
        }
    }

    private fun dispatchIntent(intent: VideoPlayerIntent) {
        when (intent) {
            is VideoPlayerIntent.VideoViewGotClickedIntent -> processOnReadyVideoViewClickIntent(state.value as? VideoPlayerContentState.VideoPlayerReadyContentState)
            is VideoPlayerIntent.VideoReadyPlayerIntent -> processVideoReadyPlayerIntent(intent)
            is VideoPlayerIntent.CleanupVideoPlayerIntent -> {
                releaseResources()
            }
            is VideoPlayerIntent.ReinitializeVideoPlayerIntent.StartFromBeginningVideoPlayerIntent -> {
                initialize()
            }
            is VideoPlayerIntent.ReinitializeVideoPlayerIntent.ContinuePlaybackVideoPlayerIntent -> {
                initialize(intent.playbackPosition)
            }
        }
    }

    /**
     * Lifecycle observer method that behaves according to state of lifecycle owner
     */
    protected fun onStateChanged(event: Lifecycle.Event, playbackPosition: Long?) {
        when (event) {
            Lifecycle.Event.ON_STOP -> {
                dispatchIntent(VideoPlayerIntent.CleanupVideoPlayerIntent)
            }
            Lifecycle.Event.ON_START -> {
                dispatchIntent(
                    VideoPlayerIntent.ReinitializeVideoPlayerIntent.ContinuePlaybackVideoPlayerIntent(
                        playbackPosition = (playerState as? VideoPlayerContentState.VideoPlayerReadyContentState)?.currentMillis
                            ?: playbackPosition
                            ?: 0
                    )
                )
            }
            else -> {}
        }
    }

    private fun changeContentState(contentState: VideoPlayerContentState) {
        uiCoroutineScope.launch { _playerStateFlow.value.emit(contentState) }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun setUpPlayer(player: ExoPlayer) {
        player.addListener(object : Player.Listener {

            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                changeContentState(
                    VideoPlayerContentState.VideoPlayerReadyContentState(
                        isEnded = reason == PlaybackState.STATE_STOPPED,
                        isPlaying = reason == PlaybackState.STATE_PLAYING && playWhenReady,
                        isLoadingVideo = reason == PlaybackState.STATE_PAUSED && playWhenReady,
                    ),
                )
            }
        })
    }

    private fun releaseResources() {
        changeContentState(
            VideoPlayerContentState.VideoPlayerReadyContentState(
                isEnded = false,
                currentMillis = exoPlayer?.currentPosition ?: 0,
                isPlaying = false
            )
        )
        exoPlayer?.release()
        exoPlayer = null
    }
    private fun processOnReadyVideoViewClickIntent(state: VideoPlayerContentState.VideoPlayerReadyContentState?) {
        if (state == null) return
        if (state.isPlaying) {
            dispatchIntent(VideoPlayerIntent.VideoReadyPlayerIntent.PauseCurrentVideo)
        } else {
            if (state.isEnded) {
                dispatchIntent(VideoPlayerIntent.VideoReadyPlayerIntent.RewindCurrentVideo())
            } else {
                dispatchIntent(VideoPlayerIntent.VideoReadyPlayerIntent.PlayCurrentVideo)
            }
        }
    }

    private fun processVideoReadyPlayerIntent(intent: VideoPlayerIntent.VideoReadyPlayerIntent) {
        val playing: Boolean
        var currentMillis = exoPlayer?.currentPosition ?: 0
        when (intent) {
            VideoPlayerIntent.VideoReadyPlayerIntent.PauseCurrentVideo -> {
                playing = false
                exoPlayer?.playWhenReady = false
            }
            VideoPlayerIntent.VideoReadyPlayerIntent.PlayCurrentVideo -> {
                playing = true
                exoPlayer?.playWhenReady = true
            }
            is VideoPlayerIntent.VideoReadyPlayerIntent.RewindCurrentVideo -> {
                exoPlayer?.seekTo(intent.millisecond)
                currentMillis = intent.millisecond
                playing = true
            }
        }
        // Uncomment when would do it with custom controls
        (playerState as? VideoPlayerContentState.VideoPlayerReadyContentState)?.let { contentStateAsPlaying ->
            changeContentState(
                contentStateAsPlaying.copy(
                    isPlaying = playing,
                    currentMillis = currentMillis
                )
            )
        }
    }
}

data class ExoPlayerConfig(
    val playOnInit: Boolean,
    val playbackPosition: Long,
    val areInFullscreenFromStart: Boolean,
    val keepScreenOnWhenPlayerInitialized: Boolean
) {
    companion object {
        val DEFAULT_PLAYER_CONFIG = ExoPlayerConfig(
            playOnInit = true,
            playbackPosition = 0,
            areInFullscreenFromStart = false,
            keepScreenOnWhenPlayerInitialized = false
        )

        fun getEnterFullscreenConfig(playbackPosition: Long, playOnInit: Boolean) = ExoPlayerConfig(
            playbackPosition = playbackPosition,
            playOnInit = playOnInit,
            areInFullscreenFromStart = true,
            keepScreenOnWhenPlayerInitialized = true
        )

        fun getExitFullscreenConfig(playbackPosition: Long, playOnInit: Boolean) = ExoPlayerConfig(
            playbackPosition = playbackPosition,
            playOnInit = playOnInit,
            areInFullscreenFromStart = false,
            keepScreenOnWhenPlayerInitialized = false
        )
    }
}