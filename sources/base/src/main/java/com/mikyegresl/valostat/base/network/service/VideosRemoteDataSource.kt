package com.mikyegresl.valostat.base.network.service

import com.mikyegresl.valostat.base.network.model.video.VideoItemResponse
import com.mikyegresl.valostat.base.network.model.video.YoutubeGeneralResponse
import com.mikyegresl.valostat.base.network.model.video.channel.ChannelItemResponse
import com.mikyegresl.valostat.base.network.model.video.stats.VideoStatDataResponse

interface VideosRemoteDataSource {

    suspend fun getChannelsOverview(): YoutubeGeneralResponse<ChannelItemResponse>

    suspend fun getVideosFromChannel(channelId: String, pageToken: String? = null): YoutubeGeneralResponse<VideoItemResponse>

    suspend fun getVideoStats(videoIds: List<String>): YoutubeGeneralResponse<VideoStatDataResponse>

}