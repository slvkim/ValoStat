package com.mikyegresl.valostat.base.converter.responseToDto.video

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.common.converter.Converter.Companion.EMPTY_STRING
import com.mikyegresl.valostat.base.model.video.ChannelDto
import com.mikyegresl.valostat.base.network.model.video.YoutubeGeneralResponse
import com.mikyegresl.valostat.base.network.model.video.channel.ChannelItemResponse

object YoutubeSearchResponseToChannelDtoConverter : Converter<YoutubeGeneralResponse<ChannelItemResponse>, ChannelDto?> {

    override fun convert(from: YoutubeGeneralResponse<ChannelItemResponse>): ChannelDto? =
        from.items?.firstOrNull()?.let { channel ->
            ChannelDto(
                channelId = channel.id?.channelId ?: EMPTY_STRING,
                title = channel.snippet?.title ?: EMPTY_STRING,
                channelTitle = channel.snippet?.channelTitle ?: EMPTY_STRING,
                description = channel.snippet?.description ?: EMPTY_STRING,
                liveBroadcastContent = channel.snippet?.liveBroadcastContent ?: EMPTY_STRING,
                thumbnailUrl = channel.snippet?.thumbnail?.default?.url ?: EMPTY_STRING,
            )
        }
}