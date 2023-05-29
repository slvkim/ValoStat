package com.mikyegresl.valostat.base.model.agent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentVoiceLineDto(
    val minDuration: Double,
    val maxDuration: Double,
    val voiceline: VoiceLineMediaDto
) : Parcelable {

    @Parcelize
    data class VoiceLineMediaDto(
        val id: Int,
        val wave: String,
        val wwise: String
    ) : Parcelable
}
