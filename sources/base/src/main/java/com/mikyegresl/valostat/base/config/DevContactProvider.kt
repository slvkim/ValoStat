package com.mikyegresl.valostat.base.config

interface DevContactProvider {

    fun getOfficialPageLink(): String

    fun getGooglePlayLink(): String
}