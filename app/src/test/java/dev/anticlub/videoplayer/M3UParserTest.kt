package dev.anticlub.videoplayer

import dev.anticlub.videoplayer.data.local.M3uParser
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class M3UParserTest {

    private lateinit var parser: M3uParser

    @Before
    fun setUp() {
        parser = M3uParser()
    }

    @Test
    fun `parse returns empty list when content is empty`() = runTest {
        val result = parser.parse("")
        assertEquals(0, result.size)
    }

    @Test
    fun `parse returns one channel with correct data`() = runTest {
        val content = """
            #EXTM3U
            #EXTINF:-1 tvg-id="id1" tvg-logo="http://logo.com/logo.png" group-title="Noticias",Canal 1
            http://stream.com/canal1.m3u8
        """.trimIndent()

        val result = parser.parse(content)

        assertEquals(1, result.size)
        assertEquals("Canal 1", result[0].name)
        assertEquals("http://stream.com/canal1.m3u8", result[0].url)
        assertEquals("id1", result[0].tvgId)
        assertEquals("http://logo.com/logo.png", result[0].logo)
        assertEquals("Noticias", result[0].group)
    }

    @Test
    fun `parse returns multiple channels`() = runTest {
        val content = """
            #EXTM3U
            #EXTINF:-1 tvg-id="id1" tvg-logo="http://logo.com/1.png" group-title="Noticias",Canal 1
            http://stream.com/canal1.m3u8
            #EXTINF:-1 tvg-id="id2" tvg-logo="http://logo.com/2.png" group-title="Deportes",Canal 2
            http://stream.com/canal2.m3u8
        """.trimIndent()

        val result = parser.parse(content)

        assertEquals(2, result.size)
        assertEquals("Canal 1", result[0].name)
        assertEquals("Canal 2", result[1].name)
    }

    @Test
    fun `parse ignores block without url`() = runTest {
        val content = """
            #EXTM3U
            #EXTINF:-1 tvg-id="id1" tvg-logo="http://logo.com/1.png" group-title="Noticias",Canal 1
            #EXTINF:-1 tvg-id="id2" tvg-logo="http://logo.com/2.png" group-title="Deportes",Canal 2
            http://stream.com/canal2.m3u8
        """.trimIndent()

        val result = parser.parse(content)

        assertEquals(1, result.size)
        assertEquals("Canal 2", result[0].name)
    }

    @Test
    fun `parse returns channel with null optional fields when not present`() = runTest {
        val content = """
            #EXTM3U
            #EXTINF:-1,Canal Sin Metadatos
            http://stream.com/canal.m3u8
        """.trimIndent()

        val result = parser.parse(content)

        assertEquals(1, result.size)
        assertEquals("Canal Sin Metadatos", result[0].name)
        assertEquals("http://stream.com/canal.m3u8", result[0].url)
        assertEquals(null, result[0].tvgId)
        assertEquals(null, result[0].logo)
        assertEquals(null, result[0].group)
    }
}