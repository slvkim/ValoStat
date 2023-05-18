package com.mikyegresl.valostat.base.converter.responseToDto.video

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.common.converter.Converter.Companion.EMPTY_STRING
import com.mikyegresl.valostat.base.model.video.ThumbnailDto
import com.mikyegresl.valostat.base.model.video.ThumbnailUrl
import com.mikyegresl.valostat.base.network.model.video.ThumbnailResponse

object ThumbnailResponseToDtoConverter : Converter<ThumbnailResponse?, ThumbnailDto> {

    override fun convert(from: ThumbnailResponse?): ThumbnailDto =

        ThumbnailDto(
            default = mapThumbnailUrl(from?.default),
            medium = mapThumbnailUrl(from?.medium),
            high = mapThumbnailUrl(from?.high),
        )

    private fun mapThumbnailUrl(from: ThumbnailResponse.ThumbnailUrlResponse?): ThumbnailUrl =
        ThumbnailUrl(
            height = from?.height ?: 0,
            width = from?.width ?: 0,
            url = from?.url ?: EMPTY_STRING
        )
}