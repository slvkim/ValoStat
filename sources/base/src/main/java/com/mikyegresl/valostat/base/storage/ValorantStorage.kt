package com.mikyegresl.valostat.base.storage

import com.google.gson.JsonElement

interface ValorantStorage {

    suspend fun getAgents(): JsonElement?

    suspend fun saveAgents(agentsJson: JsonElement)

    suspend fun removeAgents()

    suspend fun getWeapons(): JsonElement?

    suspend fun saveWeapons(weaponsJson: JsonElement)

    suspend fun removeWeapons()
}