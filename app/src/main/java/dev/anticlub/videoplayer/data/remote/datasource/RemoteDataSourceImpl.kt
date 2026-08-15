package dev.anticlub.videoplayer.data.remote.datasource

import dev.anticlub.videoplayer.data.remote.M3uService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val m3uService: M3uService
) : RemoteDataSource{
    override suspend fun getPlaylistContent(url: String): String {
        return m3uService.getPlaylistContent(url)
    }
}