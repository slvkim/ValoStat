package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.features.weapon.WeaponsViewModel
import com.mikyegresl.valostat.features.weapon.details.WeaponDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weaponsModule = module {

    viewModel {
        WeaponsViewModel(get())
    }

    viewModel { params ->
        WeaponDetailsViewModel(get(), get(), params.get())
    }
}