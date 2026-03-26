package com.example.videoplayer_kt.domain.usecases

import android.R
import com.example.videoplayer_kt.data.local.M3uParser
import com.example.videoplayer_kt.domain.repository.ChannelRepository

class ParsePlaylistUseCase(private val parser: M3uParser, private val repository: ChannelRepository) {
    suspend operator fun invoke(content: String, playlistId: Long) {
        val channelList = parser.parse(content)
        repository.saveChannels(channelList, playlistId)
    }
}