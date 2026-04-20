package com.example.videoplayer_kt.domain.models

enum class AuthErrorType {
    PASSWORD_DONT_MATCH,
    EMPTY_FIELDS,
    INVALID_CREDENTIALS,
    USER_NOT_FOUND,
    EMAIL_ALREADY_IN_USE,
    WEAK_PASSWORD,
    INVALID_EMAIL,
    NETWORK_ERROR,
    UNKNOWN
}