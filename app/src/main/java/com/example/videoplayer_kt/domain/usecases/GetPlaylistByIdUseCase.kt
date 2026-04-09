package com.example.videoplayer_kt.domain.usecases

import com.example.videoplayer_kt.domain.models.Playlist
import com.example.videoplayer_kt.domain.repository.PlaylistRepository
import javax.inject.Inject

class GetPlaylistByIdUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend operator fun invoke(playlistId: Long): Playlist?{
        return repository.getPlaylistById(playlistId)
    }
}