package com.mikyegresl.valostat.features.video

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.mikyegresl.valostat.R
import com.mikyegresl.valostat.base.model.video.ThumbnailDto
import com.mikyegresl.valostat.base.model.video.ThumbnailUrl
import com.mikyegresl.valostat.base.model.video.VideoDto
import com.mikyegresl.valostat.base.model.video.VideoStatDto
import com.mikyegresl.valostat.common.compose.ShowErrorState
import com.mikyegresl.valostat.common.compose.ShowLoadingState
import com.mikyegresl.valostat.features.video.player.YoutubeVideoPlayer
import com.mikyegresl.valostat.ui.dimen.Padding
import com.mikyegresl.valostat.ui.theme.ValoStatTypography
import com.mikyegresl.valostat.ui.theme.mainTextDark
import com.mikyegresl.valostat.ui.theme.tertiaryTextDark
import kotlinx.coroutines.flow.StateFlow

private const val TAG = "VideosScreenState"

@Preview
@Composable
fun PreviewVideoItem() {
    VideoItem(item = videoItemMock(), stats = videoStatsMock())
}

@Composable
fun VideosScreen(
    videosScreenState: StateFlow<VideosScreenState>,
    onEnterFullscreen: () -> Unit,
    onExitFullscreen: () -> Unit
) {
    val viewState = remember { videosScreenState }.collectAsStateWithLifecycle()
    val state = viewState.value

    Scaffold(
        modifier = Modifier,
        topBar = {
            VideosScreenTopBar()
        }
    ) { paddingValues ->
        when (state) {
            is VideosScreenState.VideosScreenLoadingState -> {
                ShowLoadingState()
            }
            is VideosScreenState.VideosScreenErrorState -> {
                ShowErrorState(errorMessage = state.t.message)
            }
            is VideosScreenState.VideosScreenDataState -> {
                VideosScreenAsDataState(
                    modifier = Modifier.padding(paddingValues),
                    state = state,
                    onEnterFullscreen = onEnterFullscreen,
                    onExitFullscreen = onExitFullscreen
                )
            }
        }
    }
}

@Composable
fun VideosScreenTopBar(
    modifier: Modifier = Modifier,
    pageTitle: String = stringResource(id = R.string.videos)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.Dp16, vertical = Padding.Dp16),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier
                .wrapContentWidth()
                .weight(1f),
            text = pageTitle,
            style = ValoStatTypography.h6
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideosScreenAsDataState(
    modifier: Modifier = Modifier,
    state: VideosScreenState.VideosScreenDataState,
    onEnterFullscreen: () -> Unit,
    onExitFullscreen: () -> Unit
) {
    val listState = rememberLazyListState()
    val items = state.videos
    val stats = state.stats

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            items.forEachIndexed { i, item ->
                stats[item.videoId]?.let {
                    item {
                        VideoItem(item = item, stats = it)
                        Spacer(Modifier.padding(vertical = Padding.Dp8))
                    }
                }
            }
        }
    }
}

@Composable
fun VideoItem(
    modifier: Modifier = Modifier,
    item: VideoDto,
    stats: VideoStatDto
) {
    val thumbnail = item.thumbnail.high

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.Dp16)

    ) {
        AsyncImage(
            model = thumbnail.url,
            contentScale = ContentScale.FillBounds,
            contentDescription = item.description
        )
        Column(
            modifier = Modifier
                .padding(start = Padding.Dp16, top = Padding.Dp4),
        ) {
            Text(
                text = item.title,
                style = ValoStatTypography.body1.copy(color = mainTextDark)
            )
            Spacer(modifier = Modifier.padding(top = Padding.Dp4))
            Row {
                Text(
                    text = stats.viewCount.toString(),
                    style = ValoStatTypography.body2.copy(color = tertiaryTextDark)
                )
                Spacer(modifier = Modifier.padding(horizontal = Padding.Dp4))
                Text(
                    text = item.publishedAt,
                    style = ValoStatTypography.body2.copy(color = tertiaryTextDark)
                )
            }
        }
    }
}

@Composable
fun VideoPlayer(
    videoUrl: String,
    onEnterFullscreen: () -> Unit,
    onExitFullscreen: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val youtubePlayer = remember {
        YoutubeVideoPlayer(
            videoUrl,
            lifecycleOwner,
            onEnterFullscreen,
            onExitFullscreen
        )
    }
    youtubePlayer.InflatePlayerView(modifier = Modifier.fillMaxSize())
}

private fun videoItemMock() = VideoDto(
    videoId = "7uEEISW347E",
    channelId = "UC8CX0LD98EDXl4UYX1MDCXg",
    title = "TOGETHER // 2021 VCT - VALORANT Champions",
    channelTitle = "VALORANT",
    description = "Together, we are VALORANT. We DEMAND. We DEFY. And now, we CREATE the world's first VALORANT world champions.",
    liveBroadcastContent = "none",
    publishedAt = "2021-11-30T14:00:03Z",
    publishTime = "2021-11-30T14:00:03Z",
    thumbnail = thumbnailMock()
)

private fun thumbnailMock() = ThumbnailDto(
    default = ThumbnailUrl(90, 120, "https://i.ytimg.com/vi/7uEEISW347E/default.jpg"),
    medium = ThumbnailUrl(180, 320, "https://i.ytimg.com/vi/7uEEISW347E/mqdefault.jpg"),
    high = ThumbnailUrl(480, 360, "https://i.ytimg.com/vi/7uEEISW347E/hqdefault.jpg")
)

private fun videoStatsMock() = VideoStatDto(
    viewCount = 1,
    likeCount = 1,
    favoriteCount = 1,
    commentCount = 1
)