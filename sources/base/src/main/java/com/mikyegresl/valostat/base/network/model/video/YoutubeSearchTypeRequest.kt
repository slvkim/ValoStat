package com.mikyegresl.valostat.base.network.model.video

import com.google.gson.annotations.SerializedName

enum class YoutubeSearchTypeRequest(value: String) {
    @SerializedName("channel")
    channel("channel"),
    @SerializedName("video")
    video("video"),
    @SerializedName("playlist")
    playlist("playlist")
}