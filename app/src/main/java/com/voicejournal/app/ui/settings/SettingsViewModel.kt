package com.voicejournal.app.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicejournal.app.data.local.AutoBackupPreferences
import com.voicejournal.app.data.local.BackupInterval
import com.voicejournal.app.data.repository.BackupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class BackupState {
    data object Idle : BackupState()
    data object Exporting : BackupState()
    data object Importing : BackupState()
    data class Success(val message: String) : BackupState()
    data class Error(val message: String) : BackupState()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val backupRepository: BackupRepository,
    private val autoBackupPreferences: AutoBackupPreferences
) : ViewModel() {

    private val _backupState = MutableStateFlow<BackupState>(BackupState.Idle)
    val backupState: StateFlow<BackupState> = _backupState.asStateFlow()

    val backupInterval: StateFlow<BackupInterval> = autoBackupPreferences.interval
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BackupInterval.OFF)

    val backupFolderUri: StateFlow<String?> = autoBackupPreferences.folderUri
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val lastBackupTime: StateFlow<Long> = autoBackupPreferences.lastBackupTime
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0L)

    fun setBackupInterval(interval: BackupInterval) {
        viewModelScope.launch {
            autoBackupPreferences.setInterval(interval)
        }
    }

    fun setBackupFolder(treeUri: Uri) {
        // Take persistable permission so we can write to this folder across app restarts
        try {
            appContext.contentResolver.takePersistableUriPermission(
                treeUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
        } catch (_: Exception) { }
        viewModelScope.launch {
            autoBackupPreferences.setFolderUri(treeUri.toString())
        }
    }

    fun manualBackup() {
        val folderUriStr = backupFolderUri.value
        if (folderUriStr == null) {
            _backupState.value = BackupState.Error("No backup folder selected. Please select a folder first.")
            return
        }
        viewModelScope.launch {
            _backupState.value = BackupState.Exporting
            try {
                val folderUri = Uri.parse(folderUriStr)
                val fileName = "voicejournal_backup_${System.currentTimeMillis()}.zip"
                val dir = DocumentFile.fromTreeUri(appContext, folderUri)
                val file = dir?.createFile("application/zip", fileName)
                if (file != null) {
                    backupRepository.exportToZip(file.uri)
                    autoBackupPreferences.setLastBackupTime(System.currentTimeMillis())
                    _backupState.value = BackupState.Success("Backup saved successfully")
                } else {
                    _backupState.value = BackupState.Error("Could not create backup file in selected folder")
                }
            } catch (e: Exception) {
                _backupState.value = BackupState.Error("Backup failed: ${e.message}")
            }
        }
    }

    fun runAutoBackupIfDue() {
        val interval = backupInterval.value
        val folderUri = backupFolderUri.value
        if (interval == BackupInterval.OFF || folderUri == null) return

        val lastBackup = lastBackupTime.value
        val now = System.currentTimeMillis()
        if (now - lastBackup < interval.millis) return

        viewModelScope.launch {
            try {
                val dir = DocumentFile.fromTreeUri(appContext, Uri.parse(folderUri))
                val fileName = "voicejournal_auto_${now}.zip"
                val file = dir?.createFile("application/zip", fileName)
                if (file != null) {
                    backupRepository.exportToZip(file.uri)
                    autoBackupPreferences.setLastBackupTime(now)
                }
            } catch (_: Exception) { }
        }
    }

    fun exportData(uri: Uri) {
        viewModelScope.launch {
            _backupState.value = BackupState.Exporting
            try {
                backupRepository.exportToZip(uri)
                _backupState.value = BackupState.Success("Data exported successfully")
            } catch (e: Exception) {
                _backupState.value = BackupState.Error("Export failed: ${e.message}")
            }
        }
    }

    fun importData(uri: Uri) {
        viewModelScope.launch {
            _backupState.value = BackupState.Importing
            try {
                backupRepository.importFromZip(uri)
                _backupState.value = BackupState.Success("Data imported successfully")
            } catch (e: Exception) {
                _backupState.value = BackupState.Error("Import failed: ${e.message}")
            }
        }
    }

    fun clearState() {
        _backupState.value = BackupState.Idle
    }
}
