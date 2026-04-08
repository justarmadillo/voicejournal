package com.voicejournal.app.ui.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicejournal.app.data.repository.BackupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val backupRepository: BackupRepository
) : ViewModel() {

    private val _backupState = MutableStateFlow<BackupState>(BackupState.Idle)
    val backupState: StateFlow<BackupState> = _backupState.asStateFlow()

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
