package com.mikyegresl.valostat.base.network.model.video

import com.google.gson.annotations.SerializedName

data class YoutubePageInfoResponse(
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("resultsPerPage")
    val perPage: Int
)