package com.mikyegresl.valostat.features.player

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.extractor.DefaultExtractorsFactory
import androidx.media3.extractor.ExtractorsFactory
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_ALWAYS

class BasicMediaPlayer {

    companion object {
        private const val TAG = "ExoVideoPlayer"
        private const val CACHE_CONTENT_DIRECTORY = "downloads"
        private const val CACHE_MAX_BYTES_ALLOWED = 100*1024*1024L
        private const val EXOPLAYER_USER_AGENT = "Exoplayer"
    }

    var exoPlayer: ExoPlayer? = null

//    private var downloadCache: SimpleCache? = null
    private var dataSourceFactory: DataSource.Factory? = null
    private var extractorsFactory: ExtractorsFactory? = null

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initPlayerSources(context: Context) {
//        val downloadCacheDir = File(context.getExternalFilesDir(null), CACHE_CONTENT_DIRECTORY)
//        Log.e(TAG, "initPlayerSources(): downloadCache=${downloadCache.hashCode()}")
//        downloadCache = downloadCache ?: SimpleCache(
//            downloadCacheDir,
//            LeastRecentlyUsedCacheEvictor(CACHE_MAX_BYTES_ALLOWED),
//            StandaloneDatabaseProvider(context)
//        )
//        downloadCache?.let {
//            dataSourceFactory = dataSourceFactory ?: CacheDataSource.Factory()
//                .setCache(it)
//                .setCacheWriteDataSinkFactory(CacheDataSink.Factory().setCache(it))
//                .setCacheReadDataSourceFactory(FileDataSource.Factory())
//                .setUpstreamDataSourceFactory(DefaultDataSource.Factory(
//                    context, DefaultHttpDataSource.Factory()
//                ))
//                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
//        }
        dataSourceFactory = dataSourceFactory ?: DefaultDataSource.Factory(context)
        extractorsFactory = extractorsFactory ?: DefaultExtractorsFactory()
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initPlayerUi(playerView: PlayerView, isVideoPlayer: Boolean) {
        if (isVideoPlayer) {
            with(playerView) {
                setShowBuffering(SHOW_BUFFERING_ALWAYS)
                useArtwork = true
                useController = true
                controllerHideOnTouch = true
                controllerAutoShow = true
                setControllerHideDuringAds(true)
                return
            }
        }
        playerView.useController = false
        playerView.hideController()
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun initialize(
        context: Context,
        playerView: PlayerView,
        isVideoPlayer: Boolean,
        mediaUri: String
    ) {
        initPlayerUi(playerView, isVideoPlayer)
        initPlayerSources(context)

        if (exoPlayer != null) {
            return
        }
        if (dataSourceFactory != null && extractorsFactory != null) {
            val mediaItem = MediaItem.fromUri(mediaUri)
            val mediaSourceFactory = DefaultMediaSourceFactory(
                dataSourceFactory!!, extractorsFactory!!
            )
            exoPlayer = ExoPlayer.Builder(context)
                .setMediaSourceFactory(mediaSourceFactory)
                .build()
            exoPlayer?.setMediaItem(mediaItem)
        }
    }

    fun playMedia() {
        exoPlayer?.run {
            prepare()
            play()
        }
    }

    private fun startPlayback() {
        exoPlayer?.prepare()
    }

    private fun pausePlayback() {
        exoPlayer?.stop()
    }

    private fun resumePlayback() {
        exoPlayer?.play()
    }

    fun onStateChanged(event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                startPlayback()
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
                releaseResources()
            }
            else -> {}
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun releaseResources() {
        Log.e(TAG, "releaseResources()")
//        downloadCache?.release()
//        downloadCache = null

        exoPlayer?.release()
        exoPlayer = null

        dataSourceFactory = null
        extractorsFactory = null
    }
}