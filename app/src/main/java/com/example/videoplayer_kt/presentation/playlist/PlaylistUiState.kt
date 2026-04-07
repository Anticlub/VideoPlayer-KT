package com.example.videoplayer_kt.presentation.playlist

import com.example.videoplayer_kt.domain.models.Playlist

sealed class PlaylistUiState {
    object Loading : PlaylistUiState()
    data class Success(val playlists: List<Playlist>) : PlaylistUiState()
    data class Error(val message: String) : PlaylistUiState()
}