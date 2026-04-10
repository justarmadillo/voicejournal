package com.voicejournal.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicejournal.app.audio.AudioRecorder
import com.voicejournal.app.data.repository.VoiceLogRepository
import com.voicejournal.app.domain.model.VoiceLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val voiceLogRepository: VoiceLogRepository,
    private val audioRecorder: AudioRecorder
) : ViewModel() {

    val recentLogs: StateFlow<List<VoiceLog>> = voiceLogRepository.getRecentLogs(50)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val isRecording: StateFlow<Boolean> = audioRecorder.isRecording

    private val _recordingTimer = MutableStateFlow(0L)
    val recordingTimer: StateFlow<Long> = _recordingTimer.asStateFlow()

    private val _draftSaved = MutableStateFlow(false)
    val draftSaved: StateFlow<Boolean> = _draftSaved.asStateFlow()

    fun startRecording(): String {
        return audioRecorder.startRecording()
    }

    fun stopAndSaveDraft() {
        val (fileName, durationMs) = audioRecorder.stopRecording()
        if (fileName.isBlank()) return // recording failed
        viewModelScope.launch {
            voiceLogRepository.saveDraft(fileName, durationMs)
            _draftSaved.value = true
        }
    }

    fun clearDraftSaved() {
        _draftSaved.value = false
    }

    fun cancelRecording() {
        audioRecorder.cancelRecording()
    }
}
