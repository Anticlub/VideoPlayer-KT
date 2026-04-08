package com.example.videoplayer_kt.presentation.player

sealed class PlayerUiState {
    object Loading : PlayerUiState()
    object Playing : PlayerUiState()
    data class Error(val message: String) : PlayerUiState()
}