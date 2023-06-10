package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.features.agent.AgentsViewModel
import com.mikyegresl.valostat.features.agent.details.AgentDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val agentsModule = module {

    viewModel {
        AgentsViewModel(get())
    }

    viewModel { params ->
        AgentDetailsViewModel(get(), get(), params.get())
    }
}