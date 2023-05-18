package com.mikyegresl.valostat.base.network.model.video.stats

import com.google.gson.annotations.SerializedName

data class VideoStatResponse(
    @SerializedName("viewCount")
    val viewCount: Int,
    @SerializedName("likeCount")
    val likeCount: Int,
    @SerializedName("favoriteCount")
    val favoriteCount: Int,
    @SerializedName("commentCount")
    val commentCount: Int
)
