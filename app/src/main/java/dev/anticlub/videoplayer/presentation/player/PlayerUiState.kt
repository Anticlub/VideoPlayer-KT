package dev.anticlub.videoplayer.presentation.player

import dev.anticlub.videoplayer.domain.models.PlaybackError

sealed class PlayerUiState {
    object Loading : PlayerUiState()
    object Playing : PlayerUiState()
    object Ended : PlayerUiState()
    data class Error(val error: PlaybackError) : PlayerUiState()
}