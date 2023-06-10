package com.mikyegresl.valostat.base.storage.service

import com.google.gson.JsonElement
import com.mikyegresl.valostat.base.model.ValoStatLocale

interface WeaponsLocalDataSource {

    suspend fun getWeapons(locale: ValoStatLocale): JsonElement?

    suspend fun saveWeapons(weapons: JsonElement, locale: ValoStatLocale)

    suspend fun removeWeapons(locale: ValoStatLocale)
}