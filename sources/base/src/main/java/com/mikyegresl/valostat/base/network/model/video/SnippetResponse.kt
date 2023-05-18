package com.mikyegresl.valostat.base.network.model.video

import com.google.gson.annotations.SerializedName

data class SnippetResponse(
    @SerializedName("channelId")
    val channelId: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("channelTitle")
    val channelTitle: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("liveBroadcastContent")
    val liveBroadcastContent: String? = null,
    @SerializedName("publishTime")
    val publishTime: String? = null,
    @SerializedName("publishedAt")
    val publishedAt: String? = null,
    @SerializedName("thumbnails")
    val thumbnail: ThumbnailResponse? = null
)