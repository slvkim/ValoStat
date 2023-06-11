package com.mikyegresl.valostat.features.agent.details

import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.model.agent.AgentVoiceLineDto

sealed class AgentDetailsIntent {
    data class AudioClickedIntent(
        val voiceline: AgentVoiceLineDto.VoiceLineMediaDto
    ) : AgentDetailsIntent()

    object AudioDisposeIntent : AgentDetailsIntent()

    data class UpdateAgentDetailsIntent(
        val agentId: String,
        val locale: ValoStatLocale
    ) : AgentDetailsIntent()
}
