package com.mikyegresl.valostat.base.utils

private const val YOUTUBE_LINK_PATTERN = "https://www.youtube.com"
private const val YOUTUBE_VIDEO_PATTERN = "https://youtu.be"

fun String?.isYoutubeVideo(): Boolean =
    this?.contains(YOUTUBE_LINK_PATTERN) ?: false ||
            this?.contains(YOUTUBE_VIDEO_PATTERN) ?: false