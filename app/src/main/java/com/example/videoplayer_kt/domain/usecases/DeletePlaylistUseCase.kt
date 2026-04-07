package com.example.videoplayer_kt.domain.usecases

import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.repository.PlaylistRepository
import javax.inject.Inject

class DeletePlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlist: Playlist) {
        repository.deletePlaylist(playlist)
    }
}