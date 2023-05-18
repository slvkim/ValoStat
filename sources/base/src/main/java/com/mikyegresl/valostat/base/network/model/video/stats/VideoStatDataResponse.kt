package com.mikyegresl.valostat.base.network.model.video.stats

import com.google.gson.annotations.SerializedName

data class VideoStatDataResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("etag")
    val etag: String? = null,
    @SerializedName("kind")
    val kind: String? = null,
    @SerializedName("statistics")
    val stat: VideoStatResponse? = null
)