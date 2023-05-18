package com.mikyegresl.valostat.base.network.model.video

import com.google.gson.annotations.SerializedName

data class YoutubeGeneralResponse<T>(
    @SerializedName("kind")
    val kind: String? = null,
    @SerializedName("etag")
    val eTag: String? = null,
    @SerializedName("nextPageToken")
    val nextPageToken: String? = null,
    @SerializedName("prevPageToken")
    val prevPageToken: String? = null,
    @SerializedName("pageInfo")
    val pageInfo: YoutubePageInfoResponse? = null,
    @SerializedName("items")
    val items: List<T>? = null
)
