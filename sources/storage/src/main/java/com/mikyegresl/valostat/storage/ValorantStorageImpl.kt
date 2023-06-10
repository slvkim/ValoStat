package com.mikyegresl.valostat.storage

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.mikyegresl.valostat.base.error.CacheFileNotFoundException
import com.mikyegresl.valostat.base.error.CacheMalformedException
import com.mikyegresl.valostat.base.manager.FileManager
import com.mikyegresl.valostat.base.manager.FileType
import com.mikyegresl.valostat.base.model.ValoStatLocale
import com.mikyegresl.valostat.base.storage.ValorantStorage
import java.io.ByteArrayInputStream

class ValorantStorageImpl(
    private val cacheDir: String,
    private val fileManager: FileManager,
    private val gson: Gson
) : ValorantStorage {

    private fun getAgentsCache(locale: ValoStatLocale) = "$cacheDir/$AGENTS_CACHE_FILE_NAME/${locale.title}"
    private fun getWeaponsCache(locale: ValoStatLocale) = "$cacheDir/$WEAPONS_CACHE_FILE_NAME/${locale.title}"

    companion object {
        private const val AGENTS_CACHE_FILE_NAME = "agents_cache"
        private const val WEAPONS_CACHE_FILE_NAME = "weapons_cache"
    }

    override suspend fun getAgents(locale: ValoStatLocale): JsonElement? {
        val path = getAgentsCache(locale)
        val file = fileManager.getFileIfExists(path, FileType.JSON_OBJECT)
            ?: throw CacheFileNotFoundException("No cache file at $path")

        return try {
            gson.fromJson(file.readText(), JsonElement::class.java)
        } catch (jsonEx: JsonSyntaxException) {
            throw CacheMalformedException(jsonEx)
        }
    }

    override suspend fun saveAgents(agentsJson: JsonElement, locale: ValoStatLocale) {
        val path = getAgentsCache(locale)
        val file = fileManager.getFile(path, FileType.JSON_OBJECT)
        val gsonString = gson.toJson(agentsJson)

        val stream = ByteArrayInputStream(gsonString.toByteArray())

        // following method should clear existing file and write in a clean one
        fileManager.writeFile(
            stream,
            file
        )
    }

    override suspend fun removeAgents(locale: ValoStatLocale) {
        val path = getAgentsCache(locale)
        fileManager.getFileIfExists(path, FileType.JSON_OBJECT)?.delete()
    }

    override suspend fun getWeapons(locale: ValoStatLocale): JsonElement? {
        val path = getWeaponsCache(locale)
        val file = fileManager.getFileIfExists(path, FileType.JSON_OBJECT)
            ?: throw CacheFileNotFoundException("No cache file at $path")

        return try {
            gson.fromJson(file.readText(), JsonElement::class.java)
        } catch (jsonEx: JsonSyntaxException) {
            throw CacheMalformedException(jsonEx)
        }
    }

    override suspend fun saveWeapons(weaponsJson: JsonElement, locale: ValoStatLocale) {
        val path = getWeaponsCache(locale)
        val file = fileManager.getFile(path, FileType.JSON_OBJECT)
        val gsonString = gson.toJson(weaponsJson)

        val stream = ByteArrayInputStream(gsonString.toByteArray())

        // following method should clear existing file and write in a clean one
        fileManager.writeFile(
            stream,
            file
        )
    }

    override suspend fun removeWeapons(locale: ValoStatLocale) {
        val path = getWeaponsCache(locale)
        fileManager.getFileIfExists(path, FileType.JSON_OBJECT)?.delete()
    }
}