package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.features.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val settingsModule = module {

    viewModel {
        SettingsViewModel(get())
    }
}