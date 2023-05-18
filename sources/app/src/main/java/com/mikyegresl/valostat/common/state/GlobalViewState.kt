package com.mikyegresl.valostat.common.state

import com.mikyegresl.valostat.base.error.ValoStatException

sealed class GlobalViewState {
    object Loading : GlobalViewState()
    data class Error(val error: ValoStatException) : GlobalViewState()
    object Content : GlobalViewState()
}
