package com.mikyegresl.valostat.repository

import com.mikyegresl.valostat.base.converter.responseToDto.agent.AgentsResponseToDtoConverter
import com.mikyegresl.valostat.base.database.service.AgentsLocalDataSource
import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto
import com.mikyegresl.valostat.base.network.Response
import com.mikyegresl.valostat.base.network.service.AgentsRemoteDataSource
import com.mikyegresl.valostat.base.repository.AgentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AgentsRepositoryImpl(
    private val remoteDataSource: AgentsRemoteDataSource,
    private val localDataSource: AgentsLocalDataSource
) : AgentsRepository {

    override fun getAgents(): Flow<Response<List<AgentDto>>> = flow {
        coroutineScope {
            emit(Response.Loading())
            try {
                val agents = AgentsResponseToDtoConverter.convert(
                    remoteDataSource.getAgents()
                )
                emit(
                    Response.SuccessRemote(
                        agents.filter { it.isPlayableCharacter }
                    )
                )
            } catch (e: Exception) {
                emit(Response.Error<List<AgentDto>>(e))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getAgentsOrigin(ids: List<String>): Map<String, AgentOriginDto> =
        ids.associateWith { localDataSource.getAgentOrigin(it) }

    override fun getPointsForUltimate(ids: List<String>): Map<String, Int> =
        ids.associateWith { localDataSource.getPointsForUltimate(it) }
}