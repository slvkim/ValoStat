package com.mikyegresl.valostat.base.manager

import java.io.File
import java.io.InputStream

interface FileManager {

    fun getFileId(url: String): String

    fun getFile(url: String, type: FileType): File

    fun getTempFile(url: String, type: FileType): File

    fun getFileIfExists(url: String, type: FileType): File?

    fun isFileExist(url: String, type: FileType): Boolean

    fun writeFile(
        inputStream: InputStream,
        outputFile: File,
        onProgress: (done: Long) -> Unit = {}
    ): Boolean

    fun isEnoughSpace(bytes: Long): Boolean
}
