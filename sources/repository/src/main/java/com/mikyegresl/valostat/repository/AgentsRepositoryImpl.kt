package com.mikyegresl.valostat.repository

import android.util.Log
import com.google.gson.Gson
import com.mikyegresl.valostat.base.converter.responseToDto.agent.AgentsResponseToDtoConverter
import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto
import com.mikyegresl.valostat.base.network.Response
import com.mikyegresl.valostat.base.network.model.agent.AgentsResponse
import com.mikyegresl.valostat.base.network.service.AgentsRemoteDataSource
import com.mikyegresl.valostat.base.repository.AgentsRepository
import com.mikyegresl.valostat.base.storage.service.AgentsLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.supervisorScope

class AgentsRepositoryImpl(
    private val remoteDataSource: AgentsRemoteDataSource,
    private val localDataSource: AgentsLocalDataSource,
    private val gson: Gson
) : AgentsRepository {

    companion object {
        private const val TAG = "AgentsRepository"
    }

    override fun getAgents(): Flow<Response<List<AgentDto>>> = flow {
        supervisorScope {
            emit(Response.Loading())

            val localRequest = async { localDataSource.getAgents() }
            val remoteRequest = async { remoteDataSource.getAgents() }

            var cacheLoadingSuccessful: Boolean?
            var localAgents: List<AgentDto>? = null
            val remoteAgents: List<AgentDto>?

            try {
                val localJson = localRequest.await()
                val localData = gson.fromJson(localJson, AgentsResponse::class.java)
                localAgents = AgentsResponseToDtoConverter.convert(localData)
                cacheLoadingSuccessful = true
            } catch (localEx: Exception) {
                Log.i(TAG, "Could not load cache: ${localEx.message}")
                cacheLoadingSuccessful = false
            }

            try {
                val remoteJson = remoteRequest.await()
                val remoteData = gson.fromJson(remoteJson, AgentsResponse::class.java)
                remoteAgents = AgentsResponseToDtoConverter.convert(remoteData)

                if (cacheLoadingSuccessful == false || localAgents.hashCode() != remoteAgents.hashCode()) {
                    localDataSource.saveAgents(remoteJson)
                }
                emit(
                    Response.SuccessRemote(
                        remoteAgents.filter { it.isPlayableCharacter }
                    )
                )
            } catch (remoteEx: Exception) {
                if (cacheLoadingSuccessful != true) emit(Response.Error<List<AgentDto>>(remoteEx))
                else emit(
                    Response.SuccessLocal(localAgents?.filter { it.isPlayableCharacter })
                )
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getAgentsOrigin(ids: List<String>): Map<String, AgentOriginDto> =
        ids.associateWith { localDataSource.getAgentOrigin(it) }

    override fun getPointsForUltimate(ids: List<String>): Map<String, Int> =
        ids.associateWith { localDataSource.getPointsForUltimate(it) }
}