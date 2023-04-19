package com.mikyegresl.valostat.repository

import com.mikyegresl.valostat.base.network.service.WeaponsRemoteDataSource
import com.mikyegresl.valostat.base.repository.WeaponsRepository

class WeaponsRepositoryImpl(
    private val remoteDataSource: WeaponsRemoteDataSource,
    private val localDataSource: WeaponsLocalDataSource
) : WeaponsRepository {
}