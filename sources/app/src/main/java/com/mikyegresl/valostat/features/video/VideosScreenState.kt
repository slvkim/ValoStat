package com.mikyegresl.valostat.features.video

import com.mikyegresl.valostat.base.model.video.ChannelDto
import com.mikyegresl.valostat.base.model.video.VideoDto
import com.mikyegresl.valostat.base.model.video.VideoPageInfoDto
import com.mikyegresl.valostat.base.model.video.VideoStatDto
import com.mikyegresl.valostat.common.state.BaseState

sealed class VideosScreenState: BaseState {

    object VideosScreenLoadingState : VideosScreenState()

    class VideosScreenErrorState(val t: Throwable) : VideosScreenState()

    data class VideosScreenDataState(
        val channelOverview: ChannelDto,
        val videos: List<VideoDto> = emptyList(),
        val stats: Map<String, VideoStatDto> = emptyMap(),
        val videoPageInfo: VideoPageInfoDto? = null
    ) : VideosScreenState() {

    }
}