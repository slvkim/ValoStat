package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.features.weapon.WeaponsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weaponsModule = module {

    viewModel {
        WeaponsViewModel(get())
    }
}