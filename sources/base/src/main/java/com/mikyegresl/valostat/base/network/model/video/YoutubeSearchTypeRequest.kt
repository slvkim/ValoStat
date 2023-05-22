package com.mikyegresl.valostat.base.network.model.video

import com.google.gson.annotations.SerializedName

enum class YoutubeSearchTypeRequest {
    @SerializedName("channel")
    CHANNEL,
    @SerializedName("video")
    VIDEO,
    @SerializedName("playlist")
    PLAYLIST
}