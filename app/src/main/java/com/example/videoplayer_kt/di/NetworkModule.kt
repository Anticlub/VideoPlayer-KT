package com.example.videoplayer_kt.di

import com.example.videoplayer_kt.data.remote.M3uRemoteDataSource
import com.example.videoplayer_kt.data.remote.M3uService
import com.example.videoplayer_kt.data.remote.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(service: M3uService): RemoteDataSource {
        return M3uRemoteDataSource(service)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://placeholder.com/") // baseUrl obligatoria, pero usaremos @Url
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideM3uService(retrofit: Retrofit): M3uService {
        return retrofit.create(M3uService::class.java)
    }
}