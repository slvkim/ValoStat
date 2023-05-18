package com.mikyegresl.valostat.base.network.model.video.channel

import com.google.gson.annotations.SerializedName
import com.mikyegresl.valostat.base.network.model.video.SnippetResponse

data class ChannelItemResponse(
    @SerializedName("id")
    val id: ChannelIdResponse? = null,
    @SerializedName("kind")
    val kind: String? = null,
    @SerializedName("etag")
    val eTag: String? = null,
    @SerializedName("snippet")
    val snippet: SnippetResponse? = null
)