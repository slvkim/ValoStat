package com.mikyegresl.valostat.features.player.exoplayer

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ComposableExoPlayer(
    modifier: Modifier = Modifier,
    mediaUri: String,
    playerConfig: ExoPlayerConfig,
    fullScreenListener: ExoPlayerFullScreenListener? = null
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    val player = remember {
        ExoVideoPlayer(
            mediaUrl = mediaUri,
            lifecycleOwner = lifecycleOwner,
            uiCoroutineScope = scope,
            config = playerConfig,
            fullScreenListener = fullScreenListener
        )
    }
    player.RenderPlayerView(
        modifier = modifier.background(Color.Black)
    )
}