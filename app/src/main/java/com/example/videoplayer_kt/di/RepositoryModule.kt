package com.example.videoplayer_kt.di

import com.example.videoplayer_kt.data.remote.datasource.AuthRemoteDataSource
import com.example.videoplayer_kt.data.remote.datasource.FirebaseAuthDataSourceImpl
import com.example.videoplayer_kt.data.repository.AuthRepositoryImpl
import com.example.videoplayer_kt.data.repository.ChannelRepositoryImpl
import com.example.videoplayer_kt.data.repository.PlaylistRepositoryImpl
import com.example.videoplayer_kt.domain.repository.AuthRepository
import com.example.videoplayer_kt.domain.repository.ChannelRepository
import com.example.videoplayer_kt.domain.repository.PlaylistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChannelRepository(
        impl: ChannelRepositoryImpl
    ): ChannelRepository

    @Binds
    @Singleton
    abstract fun bindPlaylistRepository(
        impl: PlaylistRepositoryImpl
    ): PlaylistRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        impl: FirebaseAuthDataSourceImpl
    ): AuthRemoteDataSource
}