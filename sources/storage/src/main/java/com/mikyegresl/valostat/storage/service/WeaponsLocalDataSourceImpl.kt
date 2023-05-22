package com.mikyegresl.valostat.storage.service

import com.google.gson.JsonElement
import com.mikyegresl.valostat.base.storage.ValorantStorage
import com.mikyegresl.valostat.base.storage.service.WeaponsLocalDataSource

class WeaponsLocalDataSourceImpl(
    private val storage: ValorantStorage
) : WeaponsLocalDataSource {

    override suspend fun getWeapons(): JsonElement? =
        storage.getWeapons()

    override suspend fun saveWeapons(weapons: JsonElement) =
        storage.saveWeapons(weapons)

    override suspend fun removeWeapons() =
        storage.removeWeapons()
}