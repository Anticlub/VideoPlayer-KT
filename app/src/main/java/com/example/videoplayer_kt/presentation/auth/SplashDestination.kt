package com.example.videoplayer_kt.presentation.auth

sealed class SplashDestination {
    object Login: SplashDestination()
    object Playlist : SplashDestination()
}