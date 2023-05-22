package com.mikyegresl.valostat.network.api

import com.google.gson.JsonElement
import retrofit2.http.GET

interface ValorantApi {

    @GET("v1/agents")
    suspend fun getAgents(): JsonElement

    @GET("v1/weapons")
    suspend fun getWeapons(): JsonElement
}