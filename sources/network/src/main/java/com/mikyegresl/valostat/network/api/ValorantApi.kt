package com.mikyegresl.valostat.network.api

import com.mikyegresl.valostat.base.network.model.agent.AgentsResponse
import com.mikyegresl.valostat.base.network.model.weapon.WeaponsResponse
import retrofit2.http.GET

interface ValorantApi {

    @GET("v1/agents")
    suspend fun getAgents(): AgentsResponse

    @GET("v1/weapons")
    suspend fun getWeapons(): WeaponsResponse
}