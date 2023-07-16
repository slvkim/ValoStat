package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.base.config.DevContactProvider
import com.mikyegresl.valostat.base.launcher.ExternalLinkLauncher
import com.mikyegresl.valostat.launcher.ExternalLinkLauncherImpl
import com.mikyegresl.valostat.provider.DevContactProviderImpl
import org.koin.dsl.module

internal val settingsModule = module {

    single<DevContactProvider> {
        DevContactProviderImpl
    }

    single<ExternalLinkLauncher> {
        ExternalLinkLauncherImpl(get())
    }
}