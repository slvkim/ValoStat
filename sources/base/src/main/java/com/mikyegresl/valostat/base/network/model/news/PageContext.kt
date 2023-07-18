package com.mikyegresl.valostat.base.network.model.news

data class PageContext(
    val articleId: String,
    val articleUid: String,
    val bcp47locale: String,
    val environment: String,
//    val i18n: I18n,
    val language: String,
    val locale: String,
//    val localizedRoutes: LocalizedRoutes,
//    val ogData: OgData,
    val originalPath: String,
    val relatedCategories: List<String>,
    val relatedTags: List<String>
)