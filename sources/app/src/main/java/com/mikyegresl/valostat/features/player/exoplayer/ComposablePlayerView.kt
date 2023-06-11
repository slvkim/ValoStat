package com.mikyegresl.valostat.features.player.exoplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.mikyegresl.valostat.features.player.exoplayer.ExoVideoPlayer

@Composable
fun ComposableExoPlayer(
    modifier: Modifier = Modifier,
    mediaUri: String,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    val player = remember {
        ExoVideoPlayer(
            mediaUrl = mediaUri,
            lifecycleOwner = lifecycleOwner,
            uiCoroutineScope = scope,
            fullScreenListener = null
        )
    }
    player.InflatePlayer(modifier = modifier)
}