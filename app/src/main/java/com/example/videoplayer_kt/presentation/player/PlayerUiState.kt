package com.example.videoplayer_kt.presentation.player

import com.example.videoplayer_kt.domain.models.PlaybackError

sealed class PlayerUiState {
    object Loading : PlayerUiState()
    object Playing : PlayerUiState()
    object Ended : PlayerUiState()
    data class Error(val error: PlaybackError) : PlayerUiState()
}