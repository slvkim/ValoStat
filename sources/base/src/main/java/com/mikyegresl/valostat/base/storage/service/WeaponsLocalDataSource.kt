package com.mikyegresl.valostat.base.storage.service

import com.google.gson.JsonElement

interface WeaponsLocalDataSource {

    suspend fun getWeapons(): JsonElement?

    suspend fun saveWeapons(weapons: JsonElement)

    suspend fun removeWeapons()
}