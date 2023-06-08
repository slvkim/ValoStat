package com.mikyegresl.valostat.base.storage

import com.mikyegresl.valostat.base.model.ValoStatLocale

interface AppConfigStorage {

    suspend fun getCurrentLocale(): ValoStatLocale

    suspend fun saveCurrentLocale(locale: ValoStatLocale)
}