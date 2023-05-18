package com.mikyegresl.valostat.app

import android.app.Application
import com.mikyegresl.valostat.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ValoStatApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ValoStatApp)
            modules(koinModules)
        }
    }
}