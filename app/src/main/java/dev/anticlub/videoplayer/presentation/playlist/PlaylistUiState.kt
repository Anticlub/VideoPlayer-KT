package dev.anticlub.videoplayer.presentation.playlist

import dev.anticlub.videoplayer.domain.models.Playlist

sealed class PlaylistUiState {
    object Loading : PlaylistUiState()
    data class Success(val playlists: List<Playlist>) : PlaylistUiState()
    data class Error(val message: String) : PlaylistUiState()
}