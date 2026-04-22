package com.example.videoplayer_kt.presentation.auth

import com.example.videoplayer_kt.domain.models.AuthErrorType

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val type: AuthErrorType) : AuthUiState()
}