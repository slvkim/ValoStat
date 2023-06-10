package com.mikyegresl.valostat.features.agent

import com.mikyegresl.valostat.base.model.ValoStatLocale

sealed class AgentsIntent {

    data class UpdateAgentsIntent(
        val locale: ValoStatLocale
    ) : AgentsIntent()
}
