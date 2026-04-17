package com.example.videoplayer_kt.domain.usecases.playlist

import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    operator fun invoke(): Flow<List<Playlist>> {
        return repository.getAllPlaylists()
    }
}