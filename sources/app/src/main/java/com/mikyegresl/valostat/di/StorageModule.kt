package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.storage.StorageFactory
import org.koin.dsl.module

internal val storageModule = module {

    single { StorageFactory(get(), get(), get()) }

    single { get<StorageFactory>().agentsLocalDataSource }

    single { get<StorageFactory>().weaponsLocalDataSource }

    single { get<StorageFactory>().appConfigStorage }

}
