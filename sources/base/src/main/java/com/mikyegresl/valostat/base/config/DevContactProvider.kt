package com.mikyegresl.valostat.base.config

interface DevContactProvider {
    fun getWhatsAppAppLink(): String

    fun getWhatsAppWebLink(): String

    fun getTelegramLink(): String

    fun getLinkedInLink(): String

    fun getGithubLink(): String

    fun getOfficialPageLink(): String

    fun getGooglePlayLink(): String
}