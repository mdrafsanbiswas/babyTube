package com.rafsan.babytube.repo

import com.rafsan.babytube.data.DataState
import com.rafsan.babytube.data.VideoItem
import com.rafsan.babytube.network.NetworkCallHandler
import com.rafsan.babytube.network.VideoApiInterface
import com.rafsan.babytube.network.convert
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoApiInterface: VideoApiInterface
) : VideoRepository {

    override suspend fun getVideos(): Flow<DataState<List<VideoItem>>> {
       return NetworkCallHandler.handleNetworkCall {
            videoApiInterface.fetchVideos().convert()
       }
    }

}