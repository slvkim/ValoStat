package com.mikyegresl.valostat.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.mikyegresl.valostat.base.manager.FileManager
import com.mikyegresl.valostat.base.network.model.video.SearchPartRequest
import com.mikyegresl.valostat.base.network.model.video.YoutubeSearchTypeRequest
import com.mikyegresl.valostat.manager.FileManagerImpl
import com.mikyegresl.valostat.utils.ChannelPartEnumDeserializer
import com.mikyegresl.valostat.utils.ChannelPartEnumSerializer
import com.mikyegresl.valostat.utils.YoutubeSearchTypeEnumDeserializer
import com.mikyegresl.valostat.utils.YoutubeSearchTypeEnumSerializer
import org.koin.dsl.module

val coreModule = module {

    single<FileManager> {
        FileManagerImpl(get<Context>().filesDir)
    }

    single {
        GsonBuilder()
            .registerTypeAdapter(SearchPartRequest::class.java, ChannelPartEnumSerializer())
            .registerTypeAdapter(SearchPartRequest::class.java, ChannelPartEnumDeserializer())
            .registerTypeAdapter(YoutubeSearchTypeRequest::class.java, YoutubeSearchTypeEnumSerializer())
            .registerTypeAdapter(YoutubeSearchTypeRequest::class.java, YoutubeSearchTypeEnumDeserializer())
            .setLenient()
            .create()
    }
}