package com.mikyegresl.valostat.base.converter.responseToDto.agent

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.model.agent.AgentVoiceLineDto
import com.mikyegresl.valostat.base.network.model.agent.AgentVoiceLineResponse

object AgentVoiceLineResponseToDtoConverter : Converter<AgentVoiceLineResponse?, AgentVoiceLineDto> {

    override fun convert(from: AgentVoiceLineResponse?): AgentVoiceLineDto =
        AgentVoiceLineDto(
            minDuration = from?.minDuration ?: 0.0,
            maxDuration = from?.maxDuration ?: 0.0,
            mediaList = VoiceMediaResponseToDtoConverter.convert(from?.mediaList),
        )
}

object VoiceMediaResponseToDtoConverter: Converter<AgentVoiceLineResponse.VoiceLineMediaResponse?, AgentVoiceLineDto.VoiceLineMediaDto> {

    override fun convert(from: AgentVoiceLineResponse.VoiceLineMediaResponse?): AgentVoiceLineDto.VoiceLineMediaDto =
        AgentVoiceLineDto.VoiceLineMediaDto(
            id = from?.id ?: 0,
            wave = from?.wave ?: Converter.EMPTY_STRING,
            wwise = from?.wwise ?: Converter.EMPTY_STRING,
        )
}
