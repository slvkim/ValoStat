package com.mikyegresl.valostat.storage

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.mikyegresl.valostat.base.manager.FileManager
import com.mikyegresl.valostat.base.storage.ValorantStorage
import com.mikyegresl.valostat.base.storage.service.AgentsLocalDataSource
import com.mikyegresl.valostat.base.storage.service.WeaponsLocalDataSource
import com.mikyegresl.valostat.storage.service.AgentsLocalDataSourceImpl
import com.mikyegresl.valostat.storage.service.WeaponsLocalDataSourceImpl

private const val MAIN_DATASTORE_FILENAME = "VALOSTAT_PREF"

private val Context.mainDataStore by preferencesDataStore(
    name = MAIN_DATASTORE_FILENAME
)

class StorageFactory(
    private val context: Context,
    private val gson: Gson,
    private val fileManager: FileManager
) {

    private val storage: ValorantStorage by lazy {
        ValorantStorageImpl(
            context.cacheDir.absolutePath, fileManager, gson
        )
    }

    val agentsLocalDataSource: AgentsLocalDataSource by lazy {
        AgentsLocalDataSourceImpl(storage)
    }

    val weaponsLocalDataSource: WeaponsLocalDataSource by lazy {
        WeaponsLocalDataSourceImpl(storage)
    }
}