package dev.anticlub.videoplayer.domain.usecases.playlist

import dev.anticlub.videoplayer.domain.models.Playlist
import dev.anticlub.videoplayer.domain.repository.PlaylistRepository
import javax.inject.Inject

class SyncPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlist: List<Playlist>){
        return repository.syncPlaylist(playlist)
    }
}