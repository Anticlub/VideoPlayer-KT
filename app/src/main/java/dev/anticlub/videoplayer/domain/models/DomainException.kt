package dev.anticlub.videoplayer.domain.models

class DomainException : Exception() {
    class AuthException(val authErrorType: AuthErrorType) : Exception()
}