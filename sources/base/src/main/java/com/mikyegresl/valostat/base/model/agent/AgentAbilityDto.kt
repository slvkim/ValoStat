package com.mikyegresl.valostat.base.model.agent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentAbilityDto(
    val displayName: String,
    val description: String,
    val displayIcon: String,
    val slot: String
) : Parcelable