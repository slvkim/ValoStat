package com.mikyegresl.valostat.base.model.agent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentRoleDto(
    val uuid: String,
    val displayName: String,
    val description: String,
    val assetPath: String,
    val displayIcon: String
) : Parcelable