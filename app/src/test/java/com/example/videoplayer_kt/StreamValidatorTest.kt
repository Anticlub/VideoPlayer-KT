package com.example.videoplayer_kt

import com.example.videoplayer_kt.domain.util.StreamValidator
import junit.framework.TestCase.assertEquals
import org.junit.Test

class StreamValidatorTest {

    @Test
    fun `isDirectStreamUrl returns true for m3u8 url`() {
        val result = StreamValidator.isDirectStreamUrl("http://stream.com/canal.m3u8")
        assertEquals(true, result)
    }

    @Test
    fun `ìsDirectStreamUrl returns true for mpd url`() {
        val result = StreamValidator.isDirectStreamUrl("http://stream.com/canal.mpd")
        assertEquals(true, result)
    }

    @Test
    fun `isDirectStreamUrl returns true for ts url `() {
        val result = StreamValidator.isDirectStreamUrl("http://stream.com/canal.ts")
        assertEquals(true, result)
    }

    @Test
    fun `isDirectStreamUrl returns false for m3u url`() {
        val result = StreamValidator.isDirectStreamUrl("http://stream.com/playlist.m3u")
        assertEquals(false, result)
    }

    @Test
    fun `isDirectStreamUrl returns true for m3u8 url with query params`() {
        val result = StreamValidator.isDirectStreamUrl("http://stream.com/canal.m3u8?token=abc")
        assertEquals(true, result)
    }
}