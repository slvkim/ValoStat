package com.mikyegresl.valostat.base.converter.responseToDto.agent

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.model.agent.AgentAbilityDto
import com.mikyegresl.valostat.base.network.model.agent.AgentAbilityResponse

object AgentAbilityResponseToDtoConverter : Converter<AgentAbilityResponse?, AgentAbilityDto> {

    override fun convert(from: AgentAbilityResponse?): AgentAbilityDto =
        AgentAbilityDto(
            displayName = from?.displayName ?: Converter.EMPTY_STRING,
            description = from?.description ?: Converter.EMPTY_STRING,
            displayIcon = from?.displayIcon ?: Converter.EMPTY_STRING,
            slot = from?.slot ?: Converter.EMPTY_STRING,
        )
}
