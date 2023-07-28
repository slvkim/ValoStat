package com.mikyegresl.valostat.base.common.converter

import com.google.gson.Gson
import com.mikyegresl.valostat.base.model.news.NewsNavigationEncoder

class NavigationEncoderFactory(
    private val gson: Gson
) {
    fun getNewsNavigationEncoder() = NewsNavigationEncoder(gson)
}