package com.mikyegresl.valostat

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mikyegresl.valostat.di.networkModule
import com.mikyegresl.valostat.di.repositoryModule

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.koin.core.context.startKoin

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                networkModule,
                repositoryModule
            )
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.mikyegresl.valostat", appContext.packageName)
    }
}