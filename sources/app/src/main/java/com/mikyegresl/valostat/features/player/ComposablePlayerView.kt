package com.mikyegresl.valostat.features.player

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.mikyegresl.valostat.features.player.exoplayer.ExoPlayerConfig
import com.mikyegresl.valostat.features.player.exoplayer.ExoPlayerFullScreenListener
import com.mikyegresl.valostat.features.player.exoplayer.ExoVideoPlayer
import kotlinx.coroutines.flow.onEach

private const val TAG = "ComposablePlayerView"

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ComposableExoPlayer(
    modifier: Modifier = Modifier,
    mediaUri: String,
    playerConfig: ExoPlayerConfig,
    isAudio: Boolean = false,
    fullScreenListener: ExoPlayerFullScreenListener? = null
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    val player = remember {
//        if (isAudio) {
//            ExoAudioPlayer(
//                mediaUrl = mediaUri,
//                lifecycleOwner = lifecycleOwner,
//                uiCoroutineScope = scope,
//                config = playerConfig
//            )
//        } else {
            ExoVideoPlayer(
                mediaUrl = mediaUri,
                lifecycleOwner = lifecycleOwner,
                uiCoroutineScope = scope,
                config = playerConfig,
                fullScreenListener = fullScreenListener
            )
//        }
    }

    LaunchedEffect(true) {
        player.state.onEach {
            when (it) {
                is VideoPlayerContentState.VideoPlayerReadyContentState -> {
                    Log.e(TAG, "state: $it")
                }
                is VideoPlayerContentState.VideoPlayerLoadingContentState -> {

                }
            }
        }
    }


    player.RenderPlayerView(
        modifier = modifier.background(Color.Black)
    )
}