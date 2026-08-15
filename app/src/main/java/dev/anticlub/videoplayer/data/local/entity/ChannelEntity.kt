package dev.anticlub.videoplayer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tvgId: String?,
    val name: String,
    val url: String,
    val group: String?,
    val logo: String?,
    val isFavorite: Boolean = false,
    val playlistId: Long, // clave foranea,
    val licenseUrl: String? = null
)
