package dev.anticlub.videoplayer.domain.util

import dev.anticlub.videoplayer.domain.models.Channel

object StreamUrlToChannelMapper {

    fun map(url: String): Channel {

        return Channel(name = url.substringAfterLast("/"), url = url)
    }
}