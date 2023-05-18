package com.mikyegresl.valostat.base.converter.responseToDto.agent

import com.mikyegresl.valostat.base.common.converter.Converter
import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.network.model.agent.AgentsResponse

object AgentsResponseToDtoConverter : Converter<AgentsResponse, List<AgentDto>> {

    override fun convert(from: AgentsResponse): List<AgentDto> {
        val result = mutableListOf<AgentDto>()

        from.data?.forEach { response ->
            val uuid = response.uuid ?: Converter.EMPTY_STRING

            result.add(
                AgentDto(
                    uuid = uuid,
                    displayName = response.displayName ?: Converter.EMPTY_STRING,
                    description = response.description ?: Converter.EMPTY_STRING,
                    background = response.background ?: Converter.EMPTY_STRING,
                    assetPath = response.assetPath ?: Converter.EMPTY_STRING,
                    displayIcon = response.displayIcon ?: Converter.EMPTY_STRING,
                    displayIconSmall = response.displayIconSmall ?: Converter.EMPTY_STRING,
                    fullPortrait = response.fullPortrait ?: Converter.EMPTY_STRING,
                    fullPortraitV2 = response.fullPortraitV2 ?: Converter.EMPTY_STRING,
                    killfeedPortrait = response.killfeedPortrait ?: Converter.EMPTY_STRING,
                    bustPortrait = response.bustPortrait ?: Converter.EMPTY_STRING,
                    developerName = response.developerName ?: Converter.EMPTY_STRING,
                    isAvailableForTest = response.isAvailableForTest ?: false,
                    isBaseContent = response.isBaseContent ?: false,
                    isFullPortraitRightFacing = response.isFullPortraitRightFacing ?: false,
                    isPlayableCharacter = response.isPlayableCharacter ?: false,
                    role = AgentRoleResponseToDtoConverter.convert(response.role),
                    voiceLine = AgentVoiceLineResponseToDtoConverter.convert(response.voiceLine),
                    abilities = AgentAbilityResponseToDtoConverter.convert(response.abilities),
                    backgroundGradientColors = response.backgroundGradientColors ?: emptyList(),
                    characterTags = response.characterTags ?: emptyList(),
                )
            )
        }
        return result
    }
}