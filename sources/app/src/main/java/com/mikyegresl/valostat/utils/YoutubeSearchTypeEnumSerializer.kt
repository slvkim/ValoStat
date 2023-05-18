package com.mikyegresl.valostat.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.mikyegresl.valostat.base.network.model.video.YoutubeSearchTypeRequest
import java.lang.reflect.Type

class YoutubeSearchTypeEnumSerializer : JsonSerializer<YoutubeSearchTypeRequest?> {

    override fun serialize(
        src: YoutubeSearchTypeRequest?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}

class YoutubeSearchTypeEnumDeserializer : JsonDeserializer<YoutubeSearchTypeRequest?> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        jsonElement: JsonElement?,
        type: Type?,
        jsonDeserializationContext: JsonDeserializationContext?
    ): YoutubeSearchTypeRequest? {
        if (jsonElement != null && jsonElement.isJsonPrimitive) {
            val channelPart = jsonElement.asString
            if (channelPart.isNotBlank()) {
                return YoutubeSearchTypeRequest.valueOf(channelPart)
            }
        }
        return null
    }
}