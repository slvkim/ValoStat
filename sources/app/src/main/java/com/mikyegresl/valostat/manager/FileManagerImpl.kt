package com.mikyegresl.valostat.manager

import android.util.Base64
import com.mikyegresl.valostat.base.manager.FileManager
import com.mikyegresl.valostat.base.manager.FileType
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class FileManagerImpl(
    private val rootDir: File,
    private val directoryName: String = FILES_FOLDER_NAME,
    private val statFsProvider: StatFsProvider = StatFsProvider
) : FileManager {

    private val statFs by lazy { statFsProvider.createStatFs(filesDir.path) }

    private val filesDir by lazy {
        File(rootDir, directoryName).also {
            if (!it.exists())
                it.mkdirs()
        }
    }
    override fun getFileId(url: String): String =
        Base64.encodeToString(url.toByteArray(), Base64.NO_WRAP).replace("=", "")

    override fun getFile(url: String, type: FileType): File {
        val fileName = getFileName(url, type)
        return File(filesDir, fileName)
    }

    private fun getFileName(url: String, type: FileType): String {
        val fileId = getFileId(url)
        return "$fileId$EXTENSION_DELIMITER${type.extension}"
    }

    override fun getTempFile(url: String, type: FileType): File {
        val fileName = getTempFileName(url, type)
        return File(filesDir, fileName)
    }

    private fun getTempFileName(url: String, type: FileType): String {
        val fileId = getFileId(url)
        val uuid = System.currentTimeMillis()
        return "$fileId-$uuid$EXTENSION_DELIMITER${type.extension}"
    }

    override fun getFileIfExists(url: String, type: FileType): File? {
        val file = getFile(url, type)
        return file.takeIf { it.exists() }
    }

    override fun isFileExist(url: String, type: FileType): Boolean =
        getFileIfExists(url, type) != null

    override fun writeFile(
        inputStream: InputStream,
        outputFile: File,
        onProgress: (done: Long) -> Unit
    ): Boolean {
        lateinit var outputStream: OutputStream
        try {
            outputFile.parentFile?.mkdirs()
            if (outputFile.exists()) {
                outputFile.delete()
            }
            val fileReader = ByteArray(BUFFER_SIZE)
            outputStream = FileOutputStream(outputFile)
            var done = 0L
            var isReading = true
            while (isReading) {
                val read = inputStream.read(fileReader)
                if (read == -1) {
                    done = -1
                    isReading = false
                } else {
                    done += read
                    outputStream.write(fileReader, 0, read)
                }
                onProgress(done)
            }
            outputStream.flush()
            return true
        } catch (e: IOException) {
            if (outputFile.exists()) {
                outputFile.delete()
            }
        } finally {
            inputStream.close()
            outputStream.close()
        }
        return false
    }

    override fun isEnoughSpace(bytes: Long): Boolean = statFs.availableBytes > bytes

    private companion object {
        const val FILES_FOLDER_NAME = "files"
        const val EXTENSION_DELIMITER = "."
        const val BUFFER_SIZE = 8094
    }
}
