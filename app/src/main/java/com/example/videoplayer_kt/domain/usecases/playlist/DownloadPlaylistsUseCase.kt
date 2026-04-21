package com.example.videoplayer_kt.domain.usecases.playlist

import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.repository.PlaylistRepository
import javax.inject.Inject

class DownloadPlaylistsUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(): List<Playlist>{
        return repository.downloadPlaylist()
    }
}