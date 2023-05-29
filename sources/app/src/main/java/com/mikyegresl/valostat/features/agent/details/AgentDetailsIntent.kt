package com.mikyegresl.valostat.features.agent.details

import com.mikyegresl.valostat.base.model.agent.AgentVoiceLineDto

sealed class AgentDetailsIntent {
    data class AudioClickedIntent(
        val voiceline: AgentVoiceLineDto.VoiceLineMediaDto
    ) : AgentDetailsIntent()

    data class AudioDisposeIntent(
        val voiceline: AgentVoiceLineDto.VoiceLineMediaDto
    ) : AgentDetailsIntent()

    data class RefreshAgentDetailsIntent(
        val agentId: String
    ) : AgentDetailsIntent()
}
