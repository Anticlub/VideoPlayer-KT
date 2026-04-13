package com.example.videoplayer_kt.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val LAST_CHANNEL_URL = stringPreferencesKey("last_channel_url")
        val LAST_CHANNEL_NAME = stringPreferencesKey("last_channel_name")
        val LAST_CHANNEL_LOGO = stringPreferencesKey("last_channel_logo")
        val LAST_CHANNEL_PLAYLIST = stringPreferencesKey("last_channel_playlist")
    }

    suspend fun saveLastChannel(url: String, name: String, logo: String, playlist: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_CHANNEL_NAME] = name
            preferences[LAST_CHANNEL_URL] = url
            preferences[LAST_CHANNEL_LOGO] = logo
            preferences[LAST_CHANNEL_PLAYLIST] = playlist
        }
    }

    val lastChannelUrl: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LAST_CHANNEL_URL] ?: ""
        }
    val lastChannelName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LAST_CHANNEL_NAME] ?: ""
        }
    val lastChannelLogo : Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LAST_CHANNEL_LOGO] ?: ""
        }
    val lastChannelPlaylist : Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LAST_CHANNEL_PLAYLIST] ?: ""
        }
}