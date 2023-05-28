package com.mikyegresl.valostat.features.video.player

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.FileDataSource
import androidx.media3.datasource.cache.CacheDataSink
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.extractor.DefaultExtractorsFactory
import androidx.media3.extractor.ExtractorsFactory
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_ALWAYS
import com.mikyegresl.valostat.ui.theme.playerBackgroundDark
import java.io.File

class ExoVideoPlayer {

    companion object {
        private const val TAG = "ExoVideoPlayer"
        private const val CACHE_CONTENT_DIRECTORY = "downloads"
        private const val CACHE_MAX_BYTES_ALLOWED = 100*1024*1024L
        private const val EXOPLAYER_USER_AGENT = "Exoplayer"
    }

    private var exoPlayer: ExoPlayer? = null
    private var exoPlayerView: PlayerView? = null

    private var databaseProvider: DatabaseProvider? = null
    private var downloadCache: SimpleCache? = null
    private var dataSourceFactory: CacheDataSource.Factory? = null
    private var extractorsFactory: ExtractorsFactory? = null

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initPlayerSources(context: Context) {
        Log.e(TAG, "initPlaySource() -> " +
                "\ndatabaseProvider=${databaseProvider.hashCode()}" +
                "\ndownloadCache=${downloadCache.hashCode()}" +
                "\ndataSourceFactory=${dataSourceFactory.hashCode()}" +
                "\nextractorsFactory=${extractorsFactory.hashCode()}")
        val downloadCacheDir = File(context.getExternalFilesDir(null), CACHE_CONTENT_DIRECTORY)

        databaseProvider = databaseProvider ?: StandaloneDatabaseProvider(context)

        databaseProvider?.let {
            downloadCache = downloadCache ?: SimpleCache(
                downloadCacheDir,
                LeastRecentlyUsedCacheEvictor(CACHE_MAX_BYTES_ALLOWED),
                it
            )
        }
        downloadCache?.let {
            dataSourceFactory = dataSourceFactory ?: CacheDataSource.Factory()
                .setCache(it)
                .setCacheWriteDataSinkFactory(CacheDataSink.Factory().setCache(it))
                .setCacheReadDataSourceFactory(FileDataSource.Factory())
                .setUpstreamDataSourceFactory(DefaultDataSource.Factory(
                    context, DefaultHttpDataSource.Factory()
                ))
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        }
        extractorsFactory = extractorsFactory ?: DefaultExtractorsFactory()
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initialize(context: Context, videoUri: String) {
        Log.e(TAG, "initialize() -> " +
                "\nexoPlayer=${exoPlayer.hashCode()}" +
                "\nplayerView=${exoPlayerView.hashCode()}")
        if (exoPlayer != null) {
            return
        }
        if (dataSourceFactory != null && extractorsFactory != null) {
            val mediaItem = MediaItem.fromUri(videoUri)
            val mediaSourceFactory = DefaultMediaSourceFactory(
                dataSourceFactory!!, extractorsFactory!!
            )
            exoPlayer = ExoPlayer.Builder(context)
                .setMediaSourceFactory(mediaSourceFactory)
                .build()
            exoPlayer?.apply {
                setMediaItem(mediaItem)
                prepare()
                play()
            }
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initPlayerUi(playerView: PlayerView) = with(playerView) {
        setShowBuffering(SHOW_BUFFERING_ALWAYS)
        useArtwork = true
        useController = true
        controllerHideOnTouch = true
        controllerAutoShow = true
        setControllerHideDuringAds(true)
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @Composable
    fun InflatePlayerView(
        modifier: Modifier = Modifier,
        videoUri: String
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        val context = LocalContext.current

        initPlayerSources(context)

        initialize(context, videoUri)

        Box(modifier = modifier) {
            DisposableEffect(
                key1 = lifecycleOwner,
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    factory = { context ->
                        PlayerView(context).apply {
                            initPlayerUi(this)
                            player = exoPlayer
                            exoPlayerView = this
                        }
                    }
                )
            ) {
                val lifecycleObserver = LifecycleEventObserver { _, event ->
                    onStateChanged(event)
                }
                lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
                onDispose {
                    releaseResources()
                    lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
                }
            }
            val isBuffering = false
            AnimatedVisibility(
                visible = isBuffering,
                modifier = Modifier.align(Alignment.Center),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(36.dp),
                    color = playerBackgroundDark
                )
            }
        }
    }

    private fun onStateChanged(event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                Log.e(TAG, "onStateChanged(): ON_START")
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.e(TAG, "onStateChanged(): ON_RESUME")
                resumePlayback()
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.e(TAG, "onStateChanged(): ON_PAUSE")
                pausePlayback()
            }
            Lifecycle.Event.ON_STOP -> {
                Log.e(TAG, "onStateChanged(): ON_STOP")
                releaseResources()
            }
            Lifecycle.Event.ON_DESTROY -> {
                Log.e(TAG, "onStateChanged(): ON_DESTROY")
            }
            else -> {

            }
        }
    }

    private fun pausePlayback() {
        exoPlayer?.stop()
    }

    private fun resumePlayback() {
        exoPlayer?.play()
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun releaseResources() {
        Log.e(TAG, "releaseResources()")
        exoPlayer?.release()
        downloadCache?.release()
        downloadCache = null
        dataSourceFactory = null
        extractorsFactory = null
        exoPlayer = null
        exoPlayerView = null
    }
}