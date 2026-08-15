package dev.anticlub.videoplayer.data.remote.datasource

import dev.anticlub.videoplayer.data.remote.dto.PlaylistDto

interface PlaylistRemoteDataSource {
    suspend fun syncPlaylists(playlists: List<PlaylistDto>)
    suspend fun uploadPlaylist(playlist: PlaylistDto)
    suspend fun downloadPlaylists(): List<PlaylistDto>
}