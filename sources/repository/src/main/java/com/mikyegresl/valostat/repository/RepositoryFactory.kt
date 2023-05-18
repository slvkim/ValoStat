package com.mikyegresl.valostat.repository

import com.mikyegresl.valostat.base.database.service.AgentsLocalDataSource
import com.mikyegresl.valostat.base.network.service.AgentsRemoteDataSource
import com.mikyegresl.valostat.base.network.service.VideosRemoteDataSource
import com.mikyegresl.valostat.base.network.service.WeaponsRemoteDataSource
import com.mikyegresl.valostat.base.repository.AgentsRepository
import com.mikyegresl.valostat.base.repository.VideosRepository
import com.mikyegresl.valostat.base.repository.WeaponsRepository

object RepositoryFactory {

    fun getAgentsRepository(
        remoteDataSource: AgentsRemoteDataSource,
        localDataSource: AgentsLocalDataSource
    ): AgentsRepository = AgentsRepositoryImpl(
        remoteDataSource,
        localDataSource
    )

    fun getWeaponsRepository(
        remoteDataSource: WeaponsRemoteDataSource,
//        localDataSource: WeaponsLocalDataSource
    ): WeaponsRepository = WeaponsRepositoryImpl(
        remoteDataSource,
//        localDataSource
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
