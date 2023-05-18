package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.storage.service.StorageFactory
import org.koin.dsl.module

internal val storageModule = module {
    single { StorageFactory(get(), get()) }

    single { get<StorageFactory>().agentsLocalDataSource }
}
