package com.mikyegresl.valostat.repository

import android.util.Log
import com.google.gson.Gson
import com.mikyegresl.valostat.base.converter.responseToDto.weapon.WeaponsResponseToDtoConverter
import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.network.Response
import com.mikyegresl.valostat.base.network.model.weapon.WeaponsResponse
import com.mikyegresl.valostat.base.network.service.WeaponsRemoteDataSource
import com.mikyegresl.valostat.base.repository.WeaponsRepository
import com.mikyegresl.valostat.base.storage.service.WeaponsLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.supervisorScope

class WeaponsRepositoryImpl(
    private val remoteDataSource: WeaponsRemoteDataSource,
    private val localDataSource: WeaponsLocalDataSource,
    private val gson: Gson
) : WeaponsRepository {

    companion object {
        private const val TAG = "WeaponsRepository"
    }

    override fun getWeapons(): Flow<Response<List<WeaponDto>>> = flow {
        supervisorScope {
            emit(Response.Loading())

            val localRequest = async { localDataSource.getWeapons() }
            val remoteRequest = async { remoteDataSource.getWeapons() }

            var cacheLoadingSuccessful: Boolean?
            var localWeapons: List<WeaponDto>? = null
            val remoteWeapons: List<WeaponDto>?

            try {
                val localJson = localRequest.await()
                val localData = gson.fromJson(localJson, WeaponsResponse::class.java)
                localWeapons = WeaponsResponseToDtoConverter.convert(localData)
                cacheLoadingSuccessful = true
            } catch (localEx: Exception) {
                Log.i(TAG, "Could not load cache: ${localEx.message}")
                cacheLoadingSuccessful = false
            }

            try {
                val remoteJson = remoteRequest.await()
                val remoteData = gson.fromJson(remoteJson, WeaponsResponse::class.java)
                remoteWeapons = WeaponsResponseToDtoConverter.convert(remoteData)

                if (cacheLoadingSuccessful == false || localWeapons.hashCode() != remoteWeapons.hashCode()) {
                    localDataSource.saveWeapons(remoteJson)
                }
                emit(
                    Response.SuccessRemote(remoteWeapons)
                )
            } catch (remoteEx: Exception) {
                if (cacheLoadingSuccessful != true) emit(Response.Error<List<WeaponDto>>(remoteEx))
                else emit(
                    Response.SuccessLocal(localWeapons)
                )
            }
        }
    }.flowOn(Dispatchers.IO)

}