package com.mikyegresl.valostat.base.network.model.video.channel

import com.google.gson.annotations.SerializedName

data class ChannelIdResponse(
    @SerializedName("kind")
    val kind: String? = null,
    @SerializedName("channelId")
    val channelId: String? = null
)
