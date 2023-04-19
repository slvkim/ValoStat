package com.mikyegresl.valostat.network.api

import com.mikyegresl.valostat.base.network.model.weapon.WeaponsResponse
import retrofit2.http.GET

interface ValorantApi {

    @GET("v1/weapons")
    suspend fun getWeapons(): WeaponsResponse
}