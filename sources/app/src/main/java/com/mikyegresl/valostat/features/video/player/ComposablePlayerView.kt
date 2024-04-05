package com.mikyegresl.valostat.features.video.player

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.mikyegresl.valostat.features.video.player.exoplayer.ExoPlayerConfig
import com.mikyegresl.valostat.features.video.player.exoplayer.ExoVideoPlayer
import com.mikyegresl.valostat.features.video.player.youtube.YoutubeVideoPlayer

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ComposableExoPlayer(
    modifier: Modifier = Modifier,
    mediaUri: String,
    playerConfig: ExoPlayerConfig,
    onEnterFullscreen: (Long, Boolean) -> Unit,
    onExitFullscreen: (Long, Boolean) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    val player = remember {
        ExoVideoPlayer(
            mediaUrl = mediaUri,
            lifecycleOwner = lifecycleOwner,
            uiCoroutineScope = scope,
            config = playerConfig,
            onEnterFullscreen = onEnterFullscreen,
            onExitFullscreen = onExitFullscreen
        )
    }
    player.RenderPlayerView(
        modifier = modifier.background(Color.Black)
    )
}

@Composable
fun ComposableYoutubePlayer(
    modifier: Modifier = Modifier,
    mediaUri: String,
    onEnterFullscreen: () -> Unit,
    onExitFullscreen: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val player = remember {
        YoutubeVideoPlayer(
            videoUrl = mediaUri,
            lifecycleOwner = lifecycleOwner,
            onYoutubeEnterFullscreen = onEnterFullscreen,
            onYoutubeExitFullscreen = onExitFullscreen,
        )
    }
    player.InflatePlayerView(modifier = modifier.background(Color.Black))
}