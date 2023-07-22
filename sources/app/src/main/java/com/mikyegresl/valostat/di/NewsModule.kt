package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.features.news.NewsViewModel
import com.mikyegresl.valostat.features.news.details.NewsDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    viewModel { NewsViewModel(get()) }
    viewModel { NewsDetailsViewModel(get()) }
}