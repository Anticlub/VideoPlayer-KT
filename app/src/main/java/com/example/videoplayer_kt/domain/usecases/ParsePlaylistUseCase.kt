package com.example.videoplayer_kt.domain.usecases

import android.R
import com.example.videoplayer_kt.data.local.M3uParser
import com.example.videoplayer_kt.domain.repository.ChannelRepository
import javax.inject.Inject

class ParsePlaylistUseCase @Inject constructor(
    private val parser: M3uParser, private val repository: ChannelRepository
) {
    suspend operator fun invoke(content: String, playlistId: Long) {
        val channelList = parser.parse(content)
        repository.saveChannels(channelList, playlistId)
    }
}