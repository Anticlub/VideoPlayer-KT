package com.example.videoplayer_kt.domain.usecases

import com.example.videoplayer_kt.data.remote.M3uService
import javax.inject.Inject

class FetchPlaylistUseCase @Inject constructor(
    private val service: M3uService
) {
    suspend operator fun invoke(url: String): String {
        return service.getPlaylistContent(url)
    }
}