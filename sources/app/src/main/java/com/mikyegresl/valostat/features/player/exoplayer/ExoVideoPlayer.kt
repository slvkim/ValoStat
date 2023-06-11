package com.mikyegresl.valostat.features.player.exoplayer

import android.content.Context
import android.media.session.PlaybackState
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.trackselection.TrackSelector
import androidx.media3.extractor.DefaultExtractorsFactory
import androidx.media3.extractor.ExtractorsFactory
import androidx.media3.ui.PlayerView
import com.mikyegresl.valostat.features.player.exoplayer.VideoPlayerContentState.VideoPlayerLoadingContentState
import com.mikyegresl.valostat.features.player.exoplayer.VideoPlayerContentState.VideoPlayerReadyContentState
import com.mikyegresl.valostat.features.player.exoplayer.VideoPlayerIntent.*
import com.mikyegresl.valostat.features.player.exoplayer.VideoPlayerIntent.VideoReadyPlayerIntent.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "PlayerVideoPlayer"
private const val PEARSON_JS_PLAYER_SIGNATURE = "mediaplayer.pearsoncmg.com"
private const val PEARSON_JS_PLAYER_URL = "https://mediaplayer.pearsoncmg.com/assets/_pmd.true/"

private val VIDEO_EXTENSIONS = listOf( "avi", "mp4" )

private const val USER_AGENT = "Exoplayer"

class ExoVideoPlayer(
    private val mediaUrl: String,
    private val lifecycleOwner: LifecycleOwner,
    val uiCoroutineScope: CoroutineScope,
    private val fullScreenListener: ExoPlayerFullScreenListener?
) {

    companion object {
        private const val DOWNLOAD_CONTENT_DIRECTORY = "downloads"
    }

    private var dataSourceFactory: DataSource.Factory? = null
    private var extractorsFactory: ExtractorsFactory? = null

    // player-related variables
    private var exoPlayer: ExoPlayer? = null
    private var trackSelector: TrackSelector? = null
    private var playerView: PlayerView? = null

    // data-related and player configuration-related variables
    private var config: ExoPlayerConfig = ExoPlayerConfig.DEFAULT_PLAYER_CONFIG
    private var dispatchedPlayOnInit = false

    private var isFullScreen: Boolean = fullScreenListener?.let { config.areInFullscreenFromStart } ?: false

    private val _playerStateFlow =
        lazy { MutableStateFlow<VideoPlayerContentState>(VideoPlayerLoadingContentState) }
    val state get() = _playerStateFlow.value.asStateFlow()
    private val playerState: VideoPlayerContentState get() = state.value

    private fun initializeWithParams(mediaUrl: String, onPlayerReady: (ExoPlayer) -> Unit): Boolean {
        return exoPlayer?.let {
            loadVideoAndPreparePlayer(mediaUrl, onPlayerReady)
            true
        } ?: false
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun loadVideoAndPreparePlayer(uri: String, onPlayerReady: (ExoPlayer) -> Unit) {
        val mediaItem = MediaItem.fromUri(uri)
//        val mediaSource = DefaultDataSource.Factory(dataSourceFactory!!).createMediaSource(mediaItem)
        exoPlayer?.let {
            onPlayerReady(it)
            it.setMediaItem(mediaItem)
            it.prepare()
        }
    }

    private fun withConfig(config: ExoPlayerConfig) = this.also {
        it.config = config
        isFullScreen = fullScreenListener?.let { config.areInFullscreenFromStart } ?: false
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initVideoPlaybackObjects(context: Context) {
//        val downloadContentDirectory = File(context.getExternalFilesDir(null), DOWNLOAD_CONTENT_DIRECTORY)
//        val downloadCache = SimpleCache(downloadContentDirectory, NoOpCacheEvictor())
//        val upstreamFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, USER_AGENT))
        dataSourceFactory = dataSourceFactory
            ?:  DefaultDataSource.Factory(context)
//                .setCache(downloadCache).setUpstreamDataSourceFactory(upstreamFactory)
        extractorsFactory = extractorsFactory ?: DefaultExtractorsFactory()
    }

    @Composable
    fun InflatePlayer(modifier: Modifier) =
        RenderPlayerView(
            modifier = modifier,
            playbackPosition = 0
        )

    @Composable
    fun ContinuePlayback(
        modifier: Modifier,
        playbackPosition: Long
    ) = RenderPlayerView(
        modifier = modifier,
        playbackPosition = playbackPosition
    )

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @Composable
    private fun RenderPlayerView(
        modifier: Modifier,
        playbackPosition: Long
    ) {
        initVideoPlaybackObjects(LocalContext.current)
        trackSelector = trackSelector ?: DefaultTrackSelector(LocalContext.current)

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
                    modifier = Modifier
                        .fillMaxSize(),
                    factory = { context ->
                        PlayerView(context).let {
                            setPlayerUi(context, it)
                            initialize(playbackPosition)
                            it.keepScreenOn = config.keepScreenOnWhenPlayerInitialized
                            playerView = it
                            it
                        }
                    }
                )
            ) {
                val lifecycleObserver = LifecycleEventObserver { _, event ->
                    onStateChanged(event, playbackPosition)
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
        val prevBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_prev)
        val nextBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_next)
        val repeatToggleBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_repeat_toggle)
        val subtitleBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_subtitle)
        val overflowBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_overflow_hide)
        val fullScreenBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_fullscreen)
        val settingsBtn = playerView.findViewById<ImageButton>(androidx.media3.ui.R.id.exo_settings)

        prevBtn.visibility = View.GONE
        nextBtn.visibility = View.GONE
        repeatToggleBtn.visibility = View.GONE
        subtitleBtn.visibility = View.GONE
        overflowBtn.visibility = View.GONE
        fullScreenBtn.visibility = View.VISIBLE
        settingsBtn.visibility = View.VISIBLE
    }

    private fun setFullscreenImageByFullscreenState(context: Context, playerView: PlayerView) {
//        val fullScreenBtn = playerView.findViewById<ImageView>(com.mikyegresl.valostat.R.id.exo_fullscreen_icon) ?: return
//
//        fullScreenBtn.takeIf { it.isVisible }?.let {
//            if (fullScreenListener == null) return
//
//            val res = if (isFullScreen) com.mikyegresl.valostat.R.drawable.ic_disable_fullscreen
//            else com.mikyegresl.valostat.R.drawable.ic_enable_fullscreen
//
//            fullScreenBtn.setImageDrawable(
//                AppCompatResources.getDrawable(context, res)
//            )
//        }
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

        val isInitialized = initializeWithParams(mediaUrl) {
            with(playbackPosition) { if (this > 0) it.seekTo(this) }
        }
        if (isInitialized) {
            changeContentState(
                VideoPlayerReadyContentState(
                    isEnded = false,
                    isPlaying = false,
                    currentMillis = config.millisOnInit
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

enum class OnResumePlayerStrategy {
    VIDEO_RESUMED,
    VIDEO_PAUSED
}

class ExoPlayerConfig(
    val onResumePlayerStrategy: OnResumePlayerStrategy = OnResumePlayerStrategy.VIDEO_PAUSED,
    val playOnInit: Boolean = false,
    val millisOnInit: Long = VideoPlayerIntent.VIDEO_START_MILLISECOND_DEFAULT,
    val areInFullscreenFromStart: Boolean = false,
    val keepScreenOnWhenPlayerInitialized: Boolean = true
) {
    companion object {
        val DEFAULT_PLAYER_CONFIG = ExoPlayerConfig(playOnInit = true)
    }
}