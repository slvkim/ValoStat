package com.mikyegresl.valostat.base.model.agent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentOriginDto(
    val countryName: String,
    val iconUrl: String
) : Parcelable