package com.mikyegresl.valostat.base.repository

import com.mikyegresl.valostat.base.model.ValoStatLocale

interface SettingsRepository {

    suspend fun saveCurrentLocale(locale: ValoStatLocale)

    suspend fun getCurrentLocale(): ValoStatLocale
}