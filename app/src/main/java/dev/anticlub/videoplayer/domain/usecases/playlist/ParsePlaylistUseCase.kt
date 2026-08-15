package dev.anticlub.videoplayer.domain.usecases.playlist

import dev.anticlub.videoplayer.data.local.M3uParser
import dev.anticlub.videoplayer.domain.repository.ChannelRepository
import javax.inject.Inject

class ParsePlaylistUseCase @Inject constructor(
    private val parser: M3uParser, private val repository: ChannelRepository
) {
    suspend operator fun invoke(content: String, playlistId: Long) {
        val channelList = parser.parse(content)
        repository.saveChannels(channelList, playlistId)
    }
}