package com.mikyegresl.valostat.base.model

enum class ValoStatLocale(val title: String) {
    EN("en-US"),
    RU("ru-RU"),
    KR("ko-KR");
//    JP("ja-JP"),
//    CH("zh-CN"),
//    FR("fr-FR"),
//    ES("es-ES"),
//    DE("de-DE")

    fun map(title: String): ValoStatLocale? =
        when (title) {
            "en-US" -> EN
            "ru-RU" -> RU
            "ko-KR" -> KR
            else -> null
    }
}