package com.mikyegresl.valostat.repository

import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.repository.SettingsRepository
import com.mikyegresl.valostat.base.storage.AppConfigStorage

class SettingsRepositoryImpl(
    private val storage: AppConfigStorage
): SettingsRepository {

    override suspend fun saveCurrentLocale(locale: ValoStatLocale) =
        storage.saveCurrentLocale(locale)

    override suspend fun getCurrentLocale(): ValoStatLocale =
        storage.getCurrentLocale()
}