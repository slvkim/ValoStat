package com.mikyegresl.valostat.storage.service

import android.content.Context
import com.google.gson.Gson
import com.mikyegresl.valostat.base.database.service.AgentsLocalDataSource

//private const val MAIN_DATASTORE_FILENAME = "SDK_CHANNELS_PREF"
//private const val CONTENT_OPTIONS_DATASTORE_FILENAME = "SDK_CHANNELS_CONTENT_OPTIONS"
//
//private val Context.mainDataStore: DataStore<Preferences> by preferencesDataStore(
//    name = MAIN_DATASTORE_FILENAME
//)
//
//private val Context.contentOptionsDataStore: DataStore<Preferences> by preferencesDataStore(
//    name = CONTENT_OPTIONS_DATASTORE_FILENAME
//)

class StorageFactory(
    private val context: Context,
    private val gson: Gson
) {
    val agentsLocalDataSource: AgentsLocalDataSource by lazy {
        AgentsLocalDataSourceImpl()
    }
}