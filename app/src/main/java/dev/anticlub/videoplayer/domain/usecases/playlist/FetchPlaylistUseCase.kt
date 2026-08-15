package dev.anticlub.videoplayer.domain.usecases.playlist

import dev.anticlub.videoplayer.data.remote.M3uRemoteDataSource
import javax.inject.Inject

class FetchPlaylistUseCase @Inject constructor(
    private val remoteDataSource: M3uRemoteDataSource
) {
    suspend operator fun invoke(url: String): String {
        return remoteDataSource.getPlaylistContent(url)
    }
}