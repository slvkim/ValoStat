package com.mikyegresl.valostat.repository

import com.mikyegresl.valostat.base.converter.responseToDto.weapon.WeaponsResponseToDtoConverter
import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.network.Response
import com.mikyegresl.valostat.base.network.service.WeaponsRemoteDataSource
import com.mikyegresl.valostat.base.repository.WeaponsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeaponsRepositoryImpl(
    private val remoteDataSource: WeaponsRemoteDataSource,
//    private val localDataSource: WeaponsLocalDataSource
) : WeaponsRepository {

    override fun getWeapons(): Flow<Response<List<WeaponDto>>> = flow {
        coroutineScope {
            emit(Response.Loading())
            try {
                val weapons = WeaponsResponseToDtoConverter.convert(remoteDataSource.getWeapons())
                emit(Response.SuccessRemote(weapons))
            } catch (e: Exception) {
                emit(Response.Error<List<WeaponDto>>(e))
            }
        }
    }.flowOn(Dispatchers.IO)

}