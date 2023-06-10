package com.mikyegresl.valostat.storage.service

import com.google.gson.JsonElement
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.storage.ValorantStorage
import com.mikyegresl.valostat.base.storage.service.WeaponsLocalDataSource

class WeaponsLocalDataSourceImpl(
    private val storage: ValorantStorage
) : WeaponsLocalDataSource {

    override suspend fun getWeapons(locale: ValoStatLocale): JsonElement? =
        storage.getWeapons(locale)

    override suspend fun saveWeapons(weapons: JsonElement, locale: ValoStatLocale) =
        storage.saveWeapons(weapons, locale)

    override suspend fun removeWeapons(locale: ValoStatLocale) =
        storage.removeWeapons(locale)
}