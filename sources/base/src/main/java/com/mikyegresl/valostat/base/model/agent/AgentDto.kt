package com.mikyegresl.valostat.base.model.agent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentDto(
    val uuid: String,
    val displayName: String,
    val description: String,
    val background: String,
    val assetPath: String,
    val displayIcon: String,
    val displayIconSmall: String,
    val fullPortrait: String,
    val fullPortraitV2: String,
    val killfeedPortrait: String,
    val bustPortrait: String,
    val developerName: String,
    val isAvailableForTest: Boolean,
    val isBaseContent: Boolean,
    val isFullPortraitRightFacing: Boolean,
    val isPlayableCharacter: Boolean,
    val role: AgentRoleDto,
    val voiceLine: AgentVoiceLineDto,
    val abilities: List<AgentAbilityDto>,
    val backgroundGradientColors: List<String>,
    val characterTags: List<String>
) : Parcelable