package com.mikyegresl.valostat.base.model.video

data class VideoPageInfoDto(
    val nextPageToken: String? = null,
    val prevPageToken: String? = null,
    val total: Int,
    val perPage: Int
)