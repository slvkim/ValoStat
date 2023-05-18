package com.mikyegresl.valostat.base.network.model.video

import com.google.gson.annotations.SerializedName

data class ThumbnailResponse(
    @SerializedName("default")
    val default: ThumbnailUrlResponse? = null,
    @SerializedName("high")
    val high: ThumbnailUrlResponse? = null,
    @SerializedName("medium")
    val medium: ThumbnailUrlResponse? = null
) {
    data class ThumbnailUrlResponse(
        @SerializedName("url")
        open val url: String? = null,
        @SerializedName("width")
        val width: Int? = null,
        @SerializedName("height")
        val height: Int? = null
    )
}