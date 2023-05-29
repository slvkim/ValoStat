package com.mikyegresl.valostat.features.agent.details

import com.mikyegresl.valostat.base.model.agent.AgentDto
import com.mikyegresl.valostat.base.model.agent.AgentOriginDto
import com.mikyegresl.valostat.base.model.agent.AgentVoiceLineDto
import com.mikyegresl.valostat.base.model.weapon.WeaponDto
import com.mikyegresl.valostat.base.model.weapon.skin.WeaponSkinChromaDto
import com.mikyegresl.valostat.common.state.BaseState
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsScreenState

sealed class AgentDetailsScreenState: BaseState {

    object AgentDetailsLoadingState: AgentDetailsScreenState()

    data class AgentDetailsErrorState(val t: Throwable) : AgentDetailsScreenState()

    data class AgentDetailsDataState(
        val details: AgentDto,
        val origin: AgentOriginDto,
        val pointsForUltimate: Int,
        val activeVoiceline: AgentVoiceLineDto.VoiceLineMediaDto? = null
    ) : AgentDetailsScreenState()
}
