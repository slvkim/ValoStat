package com.mikyegresl.valostat.base.converter.responseToDto.agent

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.model.agent.AgentRoleDto
import com.mikyegresl.valostat.base.network.model.agent.AgentRoleResponse

object AgentRoleResponseToDtoConverter : Converter<AgentRoleResponse?, AgentRoleDto> {

    override fun convert(from: AgentRoleResponse?): AgentRoleDto =
        AgentRoleDto(
            uuid = from?.uuid ?: Converter.EMPTY_STRING,
            displayName = from?.displayName ?: Converter.EMPTY_STRING,
            description = from?.description ?: Converter.EMPTY_STRING,
            assetPath = from?.assetPath ?: Converter.EMPTY_STRING,
            displayIcon = from?.displayIcon ?: Converter.EMPTY_STRING
        )
}