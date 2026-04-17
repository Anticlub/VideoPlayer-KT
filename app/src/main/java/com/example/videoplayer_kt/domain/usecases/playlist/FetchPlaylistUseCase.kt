package com.example.videoplayer_kt.domain.usecases.playlist

import com.example.videoplayer_kt.data.remote.M3uRemoteDataSource
import javax.inject.Inject

class FetchPlaylistUseCase @Inject constructor(
    private val remoteDataSource: M3uRemoteDataSource
) {
    suspend operator fun invoke(url: String): String {
        return remoteDataSource.getPlaylistContent(url)
    }
}