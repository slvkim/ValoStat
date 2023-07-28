package com.mikyegresl.valostat.base.model.news

import com.google.gson.Gson
import com.mikyegresl.valostat.base.common.converter.NavigationEncoder

class NewsNavigationEncoder(
    private val gson: Gson
): NavigationEncoder<ArticleDto>(gson) {

    override fun decode(from: String): ArticleDto =
        gson.fromJson(from, ArticleDto::class.java)
}