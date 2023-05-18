package com.mikyegresl.valostat.base.network.model.video

import com.google.gson.annotations.SerializedName

data class VideoItemResponse(
    @SerializedName("id")
    val id: VideoIdResponse? = null,
    @SerializedName("kind")
    val kind: String? = null,
    @SerializedName("etag")
    val eTag: String? = null,
    @SerializedName("snippet")
    val snippet: SnippetResponse? = null
)