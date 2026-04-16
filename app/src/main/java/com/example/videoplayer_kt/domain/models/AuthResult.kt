package com.example.videoplayer_kt.domain.models

sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val type: AuthErrorType): AuthResult<Nothing>()
}