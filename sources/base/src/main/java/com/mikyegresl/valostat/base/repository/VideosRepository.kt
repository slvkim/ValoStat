package com.mikyegresl.valostat.base.repository

import com.mikyegresl.valostat.base.model.video.ChannelDto
import com.mikyegresl.valostat.base.model.video.VideoDataDto
import com.mikyegresl.valostat.base.model.video.VideoStatDto
import com.mikyegresl.valostat.base.network.Response
import kotlinx.coroutines.flow.Flow

interface VideosRepository {

    fun getChannelOverview(): Flow<Response<ChannelDto>>

    fun getVideosFromChannel(channelId: String, pageToken: String? = null): Flow<Response<VideoDataDto>>

    fun loadVideoStats(videoIds: List<String>): Flow<Response<Map<String,VideoStatDto>>>
}