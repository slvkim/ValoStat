package com.mikyegresl.valostat.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.storage.AppConfigStorage
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AppConfigStorageImpl(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
): AppConfigStorage {

    override suspend fun getCurrentLocale(): ValoStatLocale =
        dataStore.data.map { preferences ->
            preferences[getPreferencesKey()]
                ?.takeIf { it.isNotBlank() }
                .let { jsonString ->
                    gson.fromJson(jsonString, ValoStatLocale::class.java)
                }
        }.catch {
            emit(null)
        }.firstOrNull() ?: ValoStatLocale.EN

    override suspend fun saveCurrentLocale(locale: ValoStatLocale) {
        dataStore.edit { preferences ->
            preferences[getPreferencesKey()] = gson.toJson(locale)
        }
    }

    private fun getPreferencesKey() = stringPreferencesKey("CURRENT_LOCALE")
}