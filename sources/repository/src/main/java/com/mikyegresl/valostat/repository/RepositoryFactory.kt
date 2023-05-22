package com.mikyegresl.valostat.repository

import com.google.gson.Gson
import com.mikyegresl.valostat.base.network.service.AgentsRemoteDataSource
import com.mikyegresl.valostat.base.network.service.VideosRemoteDataSource
import com.mikyegresl.valostat.base.network.service.WeaponsRemoteDataSource
import com.mikyegresl.valostat.base.repository.AgentsRepository
import com.mikyegresl.valostat.base.repository.VideosRepository
import com.mikyegresl.valostat.base.repository.WeaponsRepository
import com.mikyegresl.valostat.base.storage.service.AgentsLocalDataSource
import com.mikyegresl.valostat.base.storage.service.WeaponsLocalDataSource

object RepositoryFactory {

    fun getAgentsRepository(
        remoteDataSource: AgentsRemoteDataSource,
        localDataSource: AgentsLocalDataSource,
        gson: Gson
    ): AgentsRepository = AgentsRepositoryImpl(
        remoteDataSource,
        localDataSource,
        gson
    )

    fun getWeaponsRepository(
        remoteDataSource: WeaponsRemoteDataSource,
        localDataSource: WeaponsLocalDataSource,
        gson: Gson
    ): WeaponsRepository = WeaponsRepositoryImpl(
        remoteDataSource,
        localDataSource,
        gson
    )

//    fun getMapsRepository(
//        remoteDataSource: MapsRemoteDataSource,
//        localDataSource: MapsLocalDataSource
//    ): MapsRepository = MapsRepositoryImpl(
//        remoteDataSource,
//        localDataSource
//    )

    fun getVideosRepository(
        remoteDataSource: VideosRemoteDataSource,
    ) : VideosRepository = VideosRepositoryImpl(
        remoteDataSource
    )
}
