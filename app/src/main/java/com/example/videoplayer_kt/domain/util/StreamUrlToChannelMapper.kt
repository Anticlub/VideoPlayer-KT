package com.example.videoplayer_kt.domain.util

import com.example.videoplayer_kt.domain.models.Channel

object StreamUrlToChannelMapper {

    fun map(url: String): Channel {

        return Channel(name = url.substringAfterLast("/"), url = url)
    }
}