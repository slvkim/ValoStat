package com.mikyegresl.valostat.base.network.model.weapon

import com.google.gson.annotations.SerializedName

data class WeaponsResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("data")
    val data: List<WeaponResponse>? = null
)