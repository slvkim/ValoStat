package com.mikyegresl.valostat.base.model.video

data class VideoDataDto(
    val videos: List<VideoDto>,
    val pageInfo: VideoPageInfoDto
)