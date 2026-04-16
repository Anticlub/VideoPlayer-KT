package com.example.videoplayer_kt.data.remote.datasource

import com.example.videoplayer_kt.data.remote.M3uService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val m3uService: M3uService
) : RemoteDataSource{
    override suspend fun getPlaylistContent(url: String): String {
        return m3uService.getPlaylistContent(url)
    }
}