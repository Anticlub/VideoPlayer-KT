package dev.anticlub.videoplayer.presentation.auth

sealed class SplashDestination {
    object Login: SplashDestination()
    object Playlist : SplashDestination()
}