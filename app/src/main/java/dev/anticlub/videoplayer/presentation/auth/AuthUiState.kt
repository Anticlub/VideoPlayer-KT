package dev.anticlub.videoplayer.presentation.auth

import dev.anticlub.videoplayer.domain.models.AuthErrorType

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val type: AuthErrorType) : AuthUiState()
}