package com.example.videoplayer_kt.domain.models

class DomainException : Exception() {
    class AuthException(val authErrorType: AuthErrorType) : Exception()
}