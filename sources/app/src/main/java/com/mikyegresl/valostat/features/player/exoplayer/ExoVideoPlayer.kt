package com.mikyegresl.valostat.features.player.exoplayer

import android.content.Context
import android.media.session.PlaybackState
import android.net.Uri
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
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
import com.mikyegresl.valostat.features.player.exoplayer.VideoPlayerContentState.VideoPlayerLoadingContentState
import com.mikyegresl.valostat.features.player.exoplayer.VideoPlayerContentState.VideoPlayerReadyContentState
import com.mikyegresl.valostat.features.player.exoplayer.VideoPlayerIntent.*
import com.mikyegresl.valostat.features.player.exoplayer.VideoPlayerIntent.VideoReadyPlayerIntent.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

@UnstableApi
class ExoVideoPlayer(
    private val mediaUrl: String,
    private val lifecycleOwner: LifecycleOwner,
    val uiCoroutineScope: CoroutineScope,
    private val fullScreenListener: ExoPlayerFullScreenListener?,
    private val config: ExoPlayerConfig
) {
    companion object {
        private const val TAG = "PlayerVideoPlayer"
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

    private var isFullScreen: Boolean = fullScreenListener?.let { config.areInFullscreenFromStart } ?: false

    private val _playerStateFlow =
        lazy { MutableStateFlow<VideoPlayerContentState>(VideoPlayerLoadingContentState) }
    val state get() = _playerStateFlow.value.asStateFlow()
    private val playerState: VideoPlayerContentState get() = state.value

    private fun initializeWithParams(mediaUrl: String): Boolean {
        return exoPlayer?.let {
            loadVideoAndPreparePlayer(mediaUrl)
            true
        } ?: false
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

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @Composable
    fun RenderPlayerView(
        modifier: Modifier
    ) {
        initVideoPlaybackObjects(LocalContext.current)

        val state = _playerStateFlow.value.collectAsState()
        val dispatchedState = remember { mutableStateOf<VideoPlayerContentState?>(null) }

        key(state) {
            (state.value as? VideoPlayerReadyContentState)?.let {
                // Dispatch if present state are in state of ready
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
        val layoutParams = if (isFullScreen) {
            FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        } else {
            FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
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
        fullScreenBtn.isVisible = fullScreenListener != null

        fullScreenBtn.takeIf { it.isVisible }?.setOnClickListener {
            val params = playerView.layoutParams
            val playOnInit = exoPlayer?.isPlaying ?: false
            val playbackPosition = exoPlayer?.currentPosition ?: 0

            isFullScreen = if (fullScreenListener == null) false
            else if (isFullScreen) {
                fullScreenListener.onExitFullScreen(playbackPosition, playOnInit)
                params.height = WRAP_CONTENT
                false
            } else {
                fullScreenListener.onEnterFullScreen(playbackPosition, playOnInit)
                params.height = MATCH_PARENT
                true
            }
            params.width = MATCH_PARENT
            playerView.layoutParams = params
            setFullscreenImageByFullscreenState(context, playerView)
        }
        setFullscreenImageByFullscreenState(context, playerView)
    }

    private fun setFullscreenImageByFullscreenState(context: Context, playerView: PlayerView) {
        val fullScreenBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_fullscreen) ?: return

        if (fullScreenListener == null) return

        val res = if (isFullScreen) com.mikyegresl.valostat.R.drawable.ic_disable_fullscreen
        else com.mikyegresl.valostat.R.drawable.ic_enable_fullscreen

        fullScreenBtn.setImageDrawable(
            AppCompatResources.getDrawable(context, res)
        )
    }

    private fun reactOnVideoReadyStateOnPlayer(
        state: VideoPlayerReadyContentState,
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
            is VideoViewGotClickedIntent -> processOnReadyVideoViewClickIntent(state.value as? VideoPlayerReadyContentState)
            is VideoReadyPlayerIntent -> processVideoReadyPlayerIntent(intent)
            is CleanupVideoPlayerIntent -> {
                releaseResources()
            }
            is ReinitializeVideoPlayerIntent.StartFromBeginningVideoPlayerIntent -> {
                initialize()
            }
            is ReinitializeVideoPlayerIntent.ContinuePlaybackVideoPlayerIntent -> {
                initialize(intent.playbackPosition)
            }
        }
    }

    /**
     * Lifecycle observer method that behaves according to state of lifecycle owner
     */
    private fun onStateChanged(event: Lifecycle.Event, playbackPosition: Long?) {
        when (event) {
            Lifecycle.Event.ON_STOP -> {
                dispatchIntent(CleanupVideoPlayerIntent)
            }
            Lifecycle.Event.ON_START -> {
                dispatchIntent(
                    ReinitializeVideoPlayerIntent.ContinuePlaybackVideoPlayerIntent(
                        playbackPosition = (playerState as? VideoPlayerReadyContentState)?.currentMillis
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

            override fun onPlaybackStateChanged(playbackState: Int) {

            }

            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                changeContentState(
                    VideoPlayerReadyContentState(
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
            VideoPlayerReadyContentState(
                isEnded = false,
                currentMillis = exoPlayer?.currentPosition ?: 0,
                isPlaying = false
            )
        )
        exoPlayer?.release()
        exoPlayer = null
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
                VideoPlayerReadyContentState(
                    isEnded = false,
                    isPlaying = false,
                    currentMillis = config.playbackPosition
                )
            )
        } else {
            Log.e(ExoVideoPlayer::class.java.name, "Player has not been initialized")
        }
    }

    private fun processOnReadyVideoViewClickIntent(state: VideoPlayerReadyContentState?) {
        if (state == null) return
        if (state.isPlaying) {
            dispatchIntent(PauseCurrentVideo)
        } else {
            if (state.isEnded) {
                dispatchIntent(RewindCurrentVideo())
            } else {
                dispatchIntent(PlayCurrentVideo)
            }
        }
    }

    private fun processVideoReadyPlayerIntent(intent: VideoReadyPlayerIntent) {
        val playing: Boolean
        var currentMillis = exoPlayer?.currentPosition ?: 0
        when (intent) {
            PauseCurrentVideo -> {
                playing = false
                exoPlayer?.playWhenReady = false
            }

            PlayCurrentVideo -> {
                playing = true
                exoPlayer?.playWhenReady = true
            }

            is RewindCurrentVideo -> {
                exoPlayer?.seekTo(intent.millisecond)
                currentMillis = intent.millisecond
                playing = true
            }
        }
        // Uncomment when would do it with custom controls
        (playerState as? VideoPlayerReadyContentState)?.let { contentStateAsPlaying ->
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