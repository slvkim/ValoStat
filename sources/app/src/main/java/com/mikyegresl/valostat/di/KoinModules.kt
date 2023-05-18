package com.mikyegresl.valostat.di

import com.mikyegresl.valostat.features.weapon.di.agentsModule
import com.mikyegresl.valostat.features.weapon.di.videosModule
import com.mikyegresl.valostat.features.weapon.di.weaponsModule

val koinModules = listOf(
    coreModule,
    configModule,
    networkModule,
    storageModule,
    repositoryModule,
    agentsModule,
    weaponsModule,
    videosModule
)