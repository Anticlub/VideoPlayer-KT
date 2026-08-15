package dev.anticlub.videoplayer.data.remote

import retrofit2.http.GET
import retrofit2.http.Url

interface M3uService {
    @GET
    suspend fun getPlaylistContent(@Url url: String): String
}