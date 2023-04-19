package com.mikyegresl.valostat.base.config

interface AppConfigProvider {

    val versionCode: Int

    val versionName: String

    val isDebug: Boolean
}