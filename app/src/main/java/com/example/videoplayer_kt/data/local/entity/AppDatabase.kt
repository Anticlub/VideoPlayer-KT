package com.example.videoplayer_kt.data.local.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ChannelEntity::class, PlaylistEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun channelDao(): ChannelDao
    abstract fun playlistDao(): PlaylistDao

    companion object{
        const val DATABASE_NAME = "videoplayer_db"
    }
}