package com.mikyegresl.valostat.base.model.video

data class VideoDto(
    val videoId: String,
    val channelId: String,
    val title: String,
    val channelTitle: String,
    val description: String,
    val liveBroadcastContent: String,
    val publishedAt: String,
    val publishTime: String,
    val thumbnail: ThumbnailDto
)