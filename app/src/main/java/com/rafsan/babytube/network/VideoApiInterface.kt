package com.rafsan.babytube.network

import com.rafsan.babytube.data.VideoItem
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface VideoApiInterface {
    @GET("rafsan/babytube/data/youtube_videos.json")
    suspend fun fetchVideos(): Response<ResponseBody>
}
