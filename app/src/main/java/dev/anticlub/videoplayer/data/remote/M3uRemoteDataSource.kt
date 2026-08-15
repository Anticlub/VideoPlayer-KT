package dev.anticlub.videoplayer.data.remote

import dev.anticlub.videoplayer.data.remote.datasource.RemoteDataSource
import javax.inject.Inject

class M3uRemoteDataSource @Inject constructor(
    private val service: M3uService
) : RemoteDataSource {
    override suspend fun getPlaylistContent(url: String): String {
        return service.getPlaylistContent(url)
    }
}