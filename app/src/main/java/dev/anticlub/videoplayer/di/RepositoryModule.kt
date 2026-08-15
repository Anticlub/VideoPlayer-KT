package dev.anticlub.videoplayer.di

import dev.anticlub.videoplayer.data.remote.datasource.AuthRemoteDataSource
import dev.anticlub.videoplayer.data.remote.datasource.FirebaseAuthDataSourceImpl
import dev.anticlub.videoplayer.data.remote.datasource.FirebasePlaylistDataSourceImpl
import dev.anticlub.videoplayer.data.remote.datasource.PlaylistRemoteDataSource
import dev.anticlub.videoplayer.data.repository.AuthRepositoryImpl
import dev.anticlub.videoplayer.data.repository.ChannelRepositoryImpl
import dev.anticlub.videoplayer.data.repository.PlaylistRepositoryImpl
import dev.anticlub.videoplayer.domain.repository.AuthRepository
import dev.anticlub.videoplayer.domain.repository.ChannelRepository
import dev.anticlub.videoplayer.domain.repository.PlaylistRepository
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

    @Binds
    @Singleton
    abstract fun bindPlaylistRemoteDataSource(
        impl: FirebasePlaylistDataSourceImpl
    ) : PlaylistRemoteDataSource
}