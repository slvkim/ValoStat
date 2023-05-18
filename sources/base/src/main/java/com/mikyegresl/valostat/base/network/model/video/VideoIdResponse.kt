package com.mikyegresl.valostat.base.network.model.video

import com.google.gson.annotations.SerializedName

data class VideoIdResponse(
    @SerializedName("kind")
    val kind: String? = null,
    @SerializedName("videoId")
    val videoId: String? = null
)