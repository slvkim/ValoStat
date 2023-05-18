package com.mikyegresl.valostat.base.network.model.video

import com.google.gson.annotations.SerializedName

enum class SearchPartRequest(value: String) {
    @SerializedName("snippet")
    snippet("snippet"),
    @SerializedName("statistics")
    statistics("statistics")
}
