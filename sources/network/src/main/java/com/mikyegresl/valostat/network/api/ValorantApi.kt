package com.mikyegresl.valostat.network.api

import com.google.gson.JsonElement
import retrofit2.http.GET
import retrofit2.http.Query

interface ValorantApi {

    @GET("v1/agents")
    suspend fun getAgents(
        @Query("language") lang: String
    ): JsonElement

    @GET("v1/weapons")
    suspend fun getWeapons(
        @Query("language") lang: String
    ): JsonElement
}