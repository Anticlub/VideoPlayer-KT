package com.example.videoplayer_kt.di

import android.content.Context
import androidx.room.Room
import com.example.videoplayer_kt.data.local.UserPreferences
import com.example.videoplayer_kt.data.local.datasource.ChannelLocalDataSource
import com.example.videoplayer_kt.data.local.datasource.ChannelLocalDataSourceImp
import com.example.videoplayer_kt.data.local.datasource.PlaylistLocalDataSource
import com.example.videoplayer_kt.data.local.datasource.PlaylistLocalDataSourceImpl
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
    fun provideChannelLocalDataSource(channelDao: ChannelDao): ChannelLocalDataSource {
        return ChannelLocalDataSourceImp(channelDao)
    }

    @Provides
    @Singleton
    fun providePlaylistLocalDataSource(playlistDao: PlaylistDao) : PlaylistLocalDataSource {
        return PlaylistLocalDataSourceImpl(playlistDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
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

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }
}