package com.mikyegresl.valostat.base.converter.responseToDto.video

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.common.converter.Converter.Companion.EMPTY_STRING
import com.mikyegresl.valostat.base.model.video.VideoDataDto
import com.mikyegresl.valostat.base.model.video.VideoDto
import com.mikyegresl.valostat.base.model.video.VideoPageInfoDto
import com.mikyegresl.valostat.base.network.model.video.VideoItemResponse
import com.mikyegresl.valostat.base.network.model.video.YoutubeGeneralResponse
import com.mikyegresl.valostat.base.utils.parseDate

object YoutubeSearchResponseToVideoDtoConverter : Converter<YoutubeGeneralResponse<VideoItemResponse>, VideoDataDto> {
    override fun convert(from: YoutubeGeneralResponse<VideoItemResponse>): VideoDataDto {
        val videos = mutableListOf<VideoDto>()

        from.items?.map { item ->
            videos.add(
                VideoDto(
                    videoId = item.id?.videoId ?: EMPTY_STRING,
                    channelId = item.snippet?.channelId ?: EMPTY_STRING,
                    title = item.snippet?.title ?: EMPTY_STRING,
                    channelTitle = item.snippet?.channelTitle ?: EMPTY_STRING,
                    description = item.snippet?.description ?: EMPTY_STRING,
                    liveBroadcastContent = item.snippet?.liveBroadcastContent ?: EMPTY_STRING,
                    publishedAt =  item.snippet?.publishedAt.parseDate() ?: EMPTY_STRING,
                    publishTime = item.snippet?.publishTime ?: EMPTY_STRING,
                    thumbnail = ThumbnailResponseToDtoConverter.convert(item.snippet?.thumbnail),
                )
            )
        }
        return VideoDataDto(
            pageInfo = VideoPageInfoDto(
                nextPageToken = from.nextPageToken,
                prevPageToken = from.prevPageToken,
                total = from.pageInfo?.totalResults ?: 0,
                perPage = from.pageInfo?.perPage ?: 0
            ),
            videos = videos
        )
    }
}