package dev.anticlub.videoplayer.domain.usecases.playlist

import dev.anticlub.videoplayer.domain.models.Playlist
import dev.anticlub.videoplayer.domain.repository.PlaylistRepository
import javax.inject.Inject

class InsertPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlist: Playlist) : Long {
        return repository.insertPlaylist(playlist)
    }
}