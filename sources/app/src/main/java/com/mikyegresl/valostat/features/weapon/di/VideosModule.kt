package com.mikyegresl.valostat.features.weapon.di

import com.mikyegresl.valostat.features.video.VideosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val videosModule = module {

    viewModel {
        VideosViewModel(get())
    }
}