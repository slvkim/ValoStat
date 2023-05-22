package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.features.agent.AgentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val agentsModule = module {

    viewModel {
        AgentsViewModel(get())
    }
}