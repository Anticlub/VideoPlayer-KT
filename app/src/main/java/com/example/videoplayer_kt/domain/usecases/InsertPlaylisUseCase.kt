package com.example.videoplayer_kt.domain.usecases

import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.repository.PlaylistRepository

class InsertPlaylisUseCase(private val repository: PlaylistRepository) {
    suspend operator fun invoke(playlist: Playlist) {
        repository.insertPlaylist(playlist)
    }
}