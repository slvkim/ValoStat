package com.mikyegresl.valostat.base.model.agent

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentOriginDto(
    @StringRes val countryName: Int,
    val iconUrl: String
) : Parcelable