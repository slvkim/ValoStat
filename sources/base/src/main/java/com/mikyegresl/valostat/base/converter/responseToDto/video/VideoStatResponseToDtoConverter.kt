package com.mikyegresl.valostat.base.converter.responseToDto.video

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.model.video.VideoStatDto
import com.mikyegresl.valostat.base.network.model.video.YoutubeGeneralResponse
import com.mikyegresl.valostat.base.network.model.video.stats.VideoStatDataResponse

object VideoStatResponseToDtoConverter : Converter<YoutubeGeneralResponse<VideoStatDataResponse>, Map<String, VideoStatDto>> {

    override fun convert(from: YoutubeGeneralResponse<VideoStatDataResponse>): Map<String, VideoStatDto> =
        from.items?.associate { videoData ->
            videoData.id!! to VideoStatDto(
                viewCount = videoData.stat?.viewCount ?: 0,
                likeCount = videoData.stat?.likeCount ?: 0,
                favoriteCount = videoData.stat?.favoriteCount ?: 0,
                commentCount = videoData.stat?.commentCount ?: 0
            )
        } ?: emptyMap()
}