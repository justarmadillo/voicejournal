package com.voicejournal.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "auto_backup_prefs")

enum class BackupInterval(val label: String, val millis: Long) {
    OFF("Off", 0),
    HOURS_6("Every 6 hours", 6 * 60 * 60 * 1000L),
    HOURS_12("Every 12 hours", 12 * 60 * 60 * 1000L),
    DAILY("Daily", 24 * 60 * 60 * 1000L),
    DAYS_2("Every 2 days", 48 * 60 * 60 * 1000L),
    WEEKLY("Weekly", 7 * 24 * 60 * 60 * 1000L);
}

@Singleton
class AutoBackupPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val KEY_INTERVAL = stringPreferencesKey("backup_interval")
        private val KEY_FOLDER_URI = stringPreferencesKey("backup_folder_uri")
        private val KEY_LAST_BACKUP = longPreferencesKey("last_backup_time")
    }

    val interval: Flow<BackupInterval> = context.dataStore.data.map { prefs ->
        prefs[KEY_INTERVAL]?.let { name ->
            try { BackupInterval.valueOf(name) } catch (_: Exception) { BackupInterval.OFF }
        } ?: BackupInterval.OFF
    }

    val folderUri: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_FOLDER_URI]
    }

    val lastBackupTime: Flow<Long> = context.dataStore.data.map { prefs ->
        prefs[KEY_LAST_BACKUP] ?: 0L
    }

    suspend fun setInterval(interval: BackupInterval) {
        context.dataStore.edit { it[KEY_INTERVAL] = interval.name }
    }

    suspend fun setFolderUri(uri: String?) {
        context.dataStore.edit {
            if (uri != null) it[KEY_FOLDER_URI] = uri
            else it.remove(KEY_FOLDER_URI)
        }
    }

    suspend fun setLastBackupTime(time: Long) {
        context.dataStore.edit { it[KEY_LAST_BACKUP] = time }
    }
}
