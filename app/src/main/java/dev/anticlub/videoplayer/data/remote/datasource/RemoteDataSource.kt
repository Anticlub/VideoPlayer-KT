package dev.anticlub.videoplayer.data.remote.datasource

interface RemoteDataSource {
    suspend fun getPlaylistContent(url: String): String
}