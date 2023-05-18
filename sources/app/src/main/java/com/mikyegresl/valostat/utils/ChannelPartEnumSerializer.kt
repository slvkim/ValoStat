package com.mikyegresl.valostat.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.mikyegresl.valostat.base.network.model.video.SearchPartRequest
import java.lang.reflect.Type

class ChannelPartEnumSerializer : JsonSerializer<SearchPartRequest?> {

    override fun serialize(
        src: SearchPartRequest?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}

class ChannelPartEnumDeserializer : JsonDeserializer<SearchPartRequest?> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        jsonElement: JsonElement?,
        type: Type?,
        jsonDeserializationContext: JsonDeserializationContext?
    ): SearchPartRequest? {
        if (jsonElement != null && jsonElement.isJsonPrimitive) {
            val channelPart = jsonElement.asString
            if (channelPart.isNotBlank()) {
                return SearchPartRequest.valueOf(channelPart)
            }
        }
        return null
    }
}