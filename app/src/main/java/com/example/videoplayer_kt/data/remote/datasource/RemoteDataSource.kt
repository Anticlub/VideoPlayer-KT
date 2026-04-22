package com.example.videoplayer_kt.data.remote.datasource

interface RemoteDataSource {
    suspend fun getPlaylistContent(url: String): String
}