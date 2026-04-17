package com.example.videoplayer_kt.domain.models

sealed class DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>()
    data class Error(val type: AuthErrorType): DataResult<Nothing>()
}