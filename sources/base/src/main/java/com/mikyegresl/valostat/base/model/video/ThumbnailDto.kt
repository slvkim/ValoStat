package com.mikyegresl.valostat.base.model.video

data class ThumbnailDto(
    val default: ThumbnailUrl,
    val medium: ThumbnailUrl,
    val high: ThumbnailUrl
)

data class ThumbnailUrl(
    val height: Int,
    val width: Int,
    val url: String
)
