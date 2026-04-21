package com.example.videoplayer_kt.domain.usecases.playlist

import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.repository.PlaylistRepository
import javax.inject.Inject

class SyncPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlist: List<Playlist>){
        return repository.syncPlaylist(playlist)
    }
}