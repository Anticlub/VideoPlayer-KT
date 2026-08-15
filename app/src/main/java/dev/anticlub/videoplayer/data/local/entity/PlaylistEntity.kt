package dev.anticlub.videoplayer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val url: String,
    val type: String, //m3u o m3u8
    val lastUpdated: Long,
    val hasChannels: Boolean = false
)
