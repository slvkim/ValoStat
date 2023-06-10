package com.mikyegresl.valostat.base.storage

import com.google.gson.JsonElement
import com.mikyegresl.valostat.base.model.ValoStatLocale

interface ValorantStorage {

    suspend fun getAgents(locale: ValoStatLocale): JsonElement?

    suspend fun saveAgents(agentsJson: JsonElement, locale: ValoStatLocale)

    suspend fun removeAgents(locale: ValoStatLocale)

    suspend fun getWeapons(locale: ValoStatLocale): JsonElement?

    suspend fun saveWeapons(weaponsJson: JsonElement, locale: ValoStatLocale)

    suspend fun removeWeapons(locale: ValoStatLocale)
}