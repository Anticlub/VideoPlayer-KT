package dev.anticlub.videoplayer.data.local

import dev.anticlub.videoplayer.domain.models.Channel
import dev.anticlub.videoplayer.domain.models.DrmConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class M3uParser @Inject constructor() {
    suspend fun parse(content: String): List<Channel> = withContext(Dispatchers.IO) {
        val lines = content.lines()
        val channels = mutableListOf<Channel>()

        lines.forEachIndexed { index, line ->
            if (line.startsWith("#EXTINF")) {
                val nextLine = lines.getOrNull(index + 1)
                if (nextLine.isNullOrBlank()) {
                    return@forEachIndexed
                }
                if (nextLine.startsWith("#")) {
                    return@forEachIndexed
                }
                val url = nextLine
                val name = line.substringAfterLast(",")
                val tvgId = extractAttribute(line, "tvg-id")
                val tvgLogo = extractAttribute(line, "tvg-logo")
                val groupTitle = extractAttribute(line, "group-title")
                val licenseUrl = extractAttribute(line, "license-url")
                val drmConfig = licenseUrl?.let { DrmConfig(licenseUrl = it) }
                val channel =
                    Channel(0, tvgId, name, url, groupTitle, tvgLogo, drmConfig = drmConfig)

                channels.add(channel)

            }
        }
        channels
    }

    private fun extractAttribute(line: String, attribute: String): String? {
        val regex = Regex("""$attribute="([^"]*)""")
        return regex.find(line)?.groupValues?.get(1)
    }
}