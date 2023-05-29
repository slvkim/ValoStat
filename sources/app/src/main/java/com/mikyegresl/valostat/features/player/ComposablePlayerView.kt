package com.mikyegresl.valostat.features.player

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.PlayerView

@Composable
fun ComposablePlayerView(
    modifier: Modifier = Modifier,
    playerModifier: Modifier = Modifier,
    mediaUri: String,
    isVideoPlayer: Boolean = true
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val player = remember { BasicMediaPlayer() }

    Box(modifier = modifier) {
        DisposableEffect(
            key1 = mediaUri,
            key2 = lifecycleOwner,
            AndroidView(
                modifier = playerModifier,
                factory = { context ->
                    PlayerView(context).apply {
                        player.initialize(
                            context = context,
                            playerView = this,
                            isVideoPlayer = isVideoPlayer,
                            mediaUri = mediaUri
                        )
                        this.player = player.exoPlayer
                    }
                }
            )
        ) {
            val lifecycleObserver = LifecycleEventObserver { _, event ->
                player.onStateChanged(event)
            }
            lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
                player.releaseResources()
            }
        }
    }
}