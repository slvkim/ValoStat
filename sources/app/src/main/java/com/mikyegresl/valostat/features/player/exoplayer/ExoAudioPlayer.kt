package com.mikyegresl.valostat.features.player.exoplayer

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.features.player.AudioPlayerContentState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class ExoAudioPlayer(
    private val context: Context,
    private val mediaUrl: String,
    private val lifecycleOwner: LifecycleOwner,
    private val uiCoroutineScope: CoroutineScope
) {
    companion object {
        private var dataSourceFactory: DataSource.Factory? = null
    }

    private var exoPlayer: ExoPlayer? = null

    private val _playerStateFlow = lazy { MutableStateFlow<AudioPlayerContentState>(
        AudioPlayerContentState.AudioLoadingState
    ) }

    private val playerStateListener = object: Player.Listener {

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                Player.STATE_ENDED -> {
                    changeContentState(AudioPlayerContentState.AudioEndedState)
                }
                else -> {}
            }
        }
    }

    init {
        initPlaybackObjects()
        initAudioPlayer()
    }

    private fun play() = exoPlayer?.let {
        it.playWhenReady = false
        it.prepare()
        it.play()
        changeContentState(AudioPlayerContentState.AudioPlayingState)
    }

    private fun pause() {
        exoPlayer?.pause()
        changeContentState(AudioPlayerContentState.AudioPausedState)
    }

    private fun stop() = exoPlayer?.let {
        it.stop()
        it.seekTo(0)
    }

    private fun releaseResources() {
        changeContentState(AudioPlayerContentState.AudioEndedState)
        exoPlayer?.removeListener(playerStateListener)
        exoPlayer?.release()
        exoPlayer = null
    }

    private fun changeContentState(contentState: AudioPlayerContentState) {
        uiCoroutineScope.launch { _playerStateFlow.value.emit(contentState) }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initPlaybackObjects() {
        dataSourceFactory = dataSourceFactory
            ?: DefaultDataSource.Factory(context)
    }

    private fun initAudioPlayer() {
        exoPlayer = exoPlayer ?: ExoPlayer.Builder(context).build()
        exoPlayer?.addListener(playerStateListener)
        val audioSource = DefaultMediaSourceFactory(dataSourceFactory!!)
            .createMediaSource(MediaItem.fromUri(Uri.parse(mediaUrl)))
        exoPlayer?.setMediaSource(audioSource)
    }

    @Composable
    fun RenderPlayerView(modifier: Modifier) {
        val state = _playerStateFlow.value.collectAsState()
        val playerState = state.value
        val isPlaying = playerState is AudioPlayerContentState.AudioPlayingState

        when (playerState) {
            is AudioPlayerContentState.AudioEndedState -> {
                stop()
            }
            else -> {}
        }
        AudioPlayerView(
            modifier = modifier.wrapContentSize(),
            isPlaying = isPlaying,
            onPlayIconClicked = {
                if (isPlaying) pause()
                else play()
            }
        )
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            onStateChanged(event)
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)    }

    @Composable
    fun AudioPlayerView(
        modifier: Modifier = Modifier,
        isPlaying: Boolean,
        onPlayIconClicked: () -> Unit
    ) {
        val iconDrawable = if (isPlaying) R.drawable.ic_pause_button
        else R.drawable.ic_play_button

        Icon(
            modifier = modifier.clickable {
                onPlayIconClicked()
            },
            painter = painterResource(
                id = iconDrawable
            ),
            contentDescription = stringResource(id = R.string.voiceline)
        )
    }

    private fun onStateChanged(event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_STOP -> {
                releaseResources()
            }
            Lifecycle.Event.ON_START -> {
                initAudioPlayer()
            }
            else -> {}
        }
    }
}