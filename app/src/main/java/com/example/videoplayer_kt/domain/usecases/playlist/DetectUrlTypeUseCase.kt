package com.example.videoplayer_kt.domain.usecases.playlist

import com.example.videoplayer_kt.domain.models.Channel
import com.example.videoplayer_kt.domain.util.StreamUrlToChannelMapper
import com.example.videoplayer_kt.domain.util.StreamValidator
import javax.inject.Inject

class DetectUrlTypeUseCase @Inject constructor() {
    operator fun invoke(url: String): Channel? {
        return if (StreamValidator.isDirectStreamUrl(url)) StreamUrlToChannelMapper.map(url) else null
    }
}