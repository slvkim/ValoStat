package com.mikyegresl.valostat.repository

import com.mikyegresl.valostat.base.converter.responseToDto.video.VideoStatResponseToDtoConverter
import com.mikyegresl.valostat.base.converter.responseToDto.video.YoutubeSearchResponseToChannelDtoConverter
import com.mikyegresl.valostat.base.converter.responseToDto.video.YoutubeSearchResponseToVideoDtoConverter
import com.mikyegresl.valostat.base.model.video.ChannelDto
import com.mikyegresl.valostat.base.model.video.VideoDataDto
import com.mikyegresl.valostat.base.model.video.VideoStatDto
import com.mikyegresl.valostat.base.network.Response
import com.mikyegresl.valostat.base.network.service.VideosRemoteDataSource
import com.mikyegresl.valostat.base.repository.VideosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class VideosRepositoryImpl(
    private val remoteDataSource: VideosRemoteDataSource
) : VideosRepository {

    companion object {
        private const val TAG = "VideosRepository"
    }

    override fun getChannelOverview(): Flow<Response<ChannelDto>> = flow {
        coroutineScope {
            emit(Response.Loading())
            try {
                val channel = YoutubeSearchResponseToChannelDtoConverter
                    .convert(remoteDataSource.getChannelsOverview())
                emit(Response.SuccessRemote(channel))
            } catch (e: Exception) {
                emit(Response.Error<ChannelDto>(e))
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun getVideosFromChannel(
        channelId: String,
        pageToken: String?
    ): Flow<Response<VideoDataDto>> = flow {
        coroutineScope {
            emit(Response.Loading())
            try {
                val videos = YoutubeSearchResponseToVideoDtoConverter
                    .convert(remoteDataSource.getVideosFromChannel(channelId, pageToken))
                emit(Response.SuccessRemote(videos))
            } catch (e: Exception) {
                emit(Response.Error<VideoDataDto>(e))
            }
        }
    }

    override fun loadVideoStats(videoIds: List<String>): Flow<Response<Map<String, VideoStatDto>>> =
        flow {
            coroutineScope {
                emit(Response.Loading())
                try {
                    emit(
                        Response.SuccessRemote(
                            VideoStatResponseToDtoConverter
                                .convert(remoteDataSource.getVideoStats(videoIds))
                        )
                    )
                } catch (e: Exception) {
                    emit(Response.Error<Map<String, VideoStatDto>>(e))
                }
            }
        }
}