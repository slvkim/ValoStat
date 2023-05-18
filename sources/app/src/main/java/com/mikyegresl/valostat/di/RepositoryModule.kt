package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.repository.RepositoryFactory
import org.koin.dsl.module

val repositoryModule = module {
    single { RepositoryFactory }
    single { get<RepositoryFactory>().getAgentsRepository(get(), get()) }
    single { get<RepositoryFactory>().getWeaponsRepository(get()) }
    single { get<RepositoryFactory>().getVideosRepository(get()) }
}