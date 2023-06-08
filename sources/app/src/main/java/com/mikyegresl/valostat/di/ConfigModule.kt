package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.base.config.AppConfigProvider
import com.mikyegresl.valostat.base.config.ValoStatConfigProvider
import com.mikyegresl.valostat.providers.AppConfigProviderImpl
import com.mikyegresl.valostat.providers.ValoStatConfigProviderImpl
import org.koin.dsl.module

val configModule = module {

    single<AppConfigProvider> { AppConfigProviderImpl }
    single<ValoStatConfigProvider> { ValoStatConfigProviderImpl }
}