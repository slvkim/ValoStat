package com.mikyegresl.valostat.base.network.model.video

import com.google.gson.annotations.SerializedName

enum class SearchPartRequest {
    @SerializedName("snippet")
    SNIPPET,
    @SerializedName("statistics")
    STATISTICS
}
