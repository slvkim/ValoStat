package com.mikyegresl.valostat.base.manager

enum class FileType(
    val extension: String,
    val mime: String,
    val possibleExtensions: List<String> = emptyList()
) {

    PDF("pdf", "application/pdf"),
    IMAGE("jpeg", "image", listOf("jpeg", "jpg", "png")),
    JSON_OBJECT("json", "text");

    companion object {
        fun find(extension: String): FileType? = values().firstOrNull { it.extension == extension }
    }
}
