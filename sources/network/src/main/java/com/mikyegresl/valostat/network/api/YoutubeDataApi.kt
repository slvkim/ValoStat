package com.mikyegresl.valostat.network.api

import com.mikyegresl.valostat.base.network.model.video.SearchPartRequest
import com.mikyegresl.valostat.base.network.model.video.VideoItemResponse
import com.mikyegresl.valostat.base.network.model.video.YoutubeGeneralResponse
import com.mikyegresl.valostat.base.network.model.video.YoutubeSearchTypeRequest
import com.mikyegresl.valostat.base.network.model.video.channel.ChannelItemResponse
import com.mikyegresl.valostat.base.network.model.video.stats.VideoStatDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeDataApi {

    @GET("v3/search")
    suspend fun getChannelsOverview(
        @Query("part") part: SearchPartRequest = SearchPartRequest.SNIPPET,
        @Query("type") type: YoutubeSearchTypeRequest = YoutubeSearchTypeRequest.CHANNEL,
        @Query("maxResults") maxResults: Int,
        @Query("q") query: String
    ): YoutubeGeneralResponse<ChannelItemResponse>

    @GET("v3/search")
    suspend fun getVideosFromChannel(
        @Query("part") part: SearchPartRequest = SearchPartRequest.SNIPPET,
        @Query("type") type: YoutubeSearchTypeRequest = YoutubeSearchTypeRequest.VIDEO,
        @Query("pageToken") pageToken: String? = null,
        @Query("channelId") channelId: String
    ): YoutubeGeneralResponse<VideoItemResponse>

    @GET("v3/videos")
    suspend fun getVideosStats(
        @Query("part") part: SearchPartRequest = SearchPartRequest.STATISTICS,
        @Query("pageToken") pageToken: String? = null,
        @Query("id") ids: List<String>
    ): YoutubeGeneralResponse<VideoStatDataResponse>
}