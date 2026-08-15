package dev.anticlub.videoplayer.domain.usecases.playlist

import dev.anticlub.videoplayer.domain.models.Channel
import dev.anticlub.videoplayer.domain.util.StreamUrlToChannelMapper
import dev.anticlub.videoplayer.domain.util.StreamValidator
import javax.inject.Inject

class DetectUrlTypeUseCase @Inject constructor() {
    operator fun invoke(url: String): Channel? {
        return if (StreamValidator.isDirectStreamUrl(url)) StreamUrlToChannelMapper.map(url) else null
    }
}