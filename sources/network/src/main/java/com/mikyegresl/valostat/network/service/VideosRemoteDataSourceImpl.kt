package com.mikyegresl.valostat.network.service

import com.mikyegresl.valostat.base.error.ErrorHandler
import com.mikyegresl.valostat.base.network.model.video.VideoItemResponse
import com.mikyegresl.valostat.base.network.model.video.YoutubeGeneralResponse
import com.mikyegresl.valostat.base.network.model.video.channel.ChannelItemResponse
import com.mikyegresl.valostat.base.network.model.video.stats.VideoStatDataResponse
import com.mikyegresl.valostat.base.network.service.VideosRemoteDataSource
import com.mikyegresl.valostat.network.api.YoutubeDataApi

class VideosRemoteDataSourceImpl(
    private val api: YoutubeDataApi,
    private val errorHandler: ErrorHandler
) : VideosRemoteDataSource {

    companion object {
        private const val VALORANT_CHANNEL_NAME = "PlayValorant"
    }

    override suspend fun getChannelsOverview(): YoutubeGeneralResponse<ChannelItemResponse> =
        errorHandler.handleError {
            api.getChannelsOverview(
                maxResults = 1,
                query = VALORANT_CHANNEL_NAME
            )
        }

    override suspend fun getVideosFromChannel(
        channelId: String,
        pageToken: String?
    ): YoutubeGeneralResponse<VideoItemResponse> =
        errorHandler.handleError {
            api.getVideosFromChannel(
                channelId = channelId,
                pageToken = pageToken
            )
        }

    override suspend fun getVideoStats(videoIds: List<String>): YoutubeGeneralResponse<VideoStatDataResponse> =
        errorHandler.handleError {
            api.getVideosStats(
                ids = videoIds
            )
        }
}