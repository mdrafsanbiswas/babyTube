package com.rafsan.babytube.repo

import com.rafsan.babytube.data.DataState
import com.rafsan.babytube.data.VideoItem
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideos(): Flow<DataState<List<VideoItem>>>
}