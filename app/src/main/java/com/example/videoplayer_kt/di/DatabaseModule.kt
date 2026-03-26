package com.example.videoplayer_kt.di

import android.content.Context
import androidx.room.Room
import com.example.videoplayer_kt.data.local.entity.AppDatabase
import com.example.videoplayer_kt.data.local.entity.ChannelDao
import com.example.videoplayer_kt.data.local.entity.PlaylistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun provideChannelDao(database: AppDatabase): ChannelDao {
        return database.channelDao()
    }

    @Provides
    @Singleton
    fun providePlaylistDao(database: AppDatabase): PlaylistDao {
        return database.playlistDao()
    }
}