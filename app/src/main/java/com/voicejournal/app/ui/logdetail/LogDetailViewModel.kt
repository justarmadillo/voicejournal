package com.voicejournal.app.ui.logdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicejournal.app.audio.AudioPlayer
import com.voicejournal.app.audio.AudioRecorder
import com.voicejournal.app.data.local.audio.AudioFileManager
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao
import com.voicejournal.app.data.local.db.entity.VoiceNoteEntity
import com.voicejournal.app.data.repository.CategoryRepository
import com.voicejournal.app.data.repository.VoiceLogRepository
import com.voicejournal.app.domain.model.Category
import com.voicejournal.app.domain.model.VoiceLog
import com.voicejournal.app.domain.model.VoiceNote
import com.voicejournal.app.util.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LogDetailViewModel @Inject constructor(
    private val voiceLogRepository: VoiceLogRepository,
    private val categoryRepository: CategoryRepository,
    private val voiceNoteDao: VoiceNoteDao,
    private val audioFileManager: AudioFileManager,
    val audioPlayer: AudioPlayer,
    val audioRecorder: AudioRecorder,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val voiceLogId: String = savedStateHandle["voiceLogId"] ?: ""
    private var notesSaveJob: Job? = null

    val voiceLog: StateFlow<VoiceLog?> = voiceLogRepository.getByIdWithCategories(voiceLogId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allCategories: StateFlow<List<Category>> = categoryRepository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val voiceNotes: StateFlow<List<VoiceNote>> = voiceNoteDao.getByVoiceLogId(voiceLogId)
        .map { entities ->
            entities.map { VoiceNote(it.id, it.voiceLogId, it.audioFileName, it.durationMs, it.textNote, it.createdAt) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun playAudio(fileName: String) { audioPlayer.play(fileName) }
    fun pauseAudio() { audioPlayer.pause() }
    fun stopAudio() { audioPlayer.stop() }

    fun updateCategories(categoryIds: List<String>) {
        viewModelScope.launch {
            voiceLogRepository.updateCategories(voiceLogId, categoryIds)
        }
    }

    fun createCategory(name: String) {
        viewModelScope.launch {
            categoryRepository.create(name)
        }
    }

    /**
     * Debounced save: waits 500ms after last keystroke before writing to DB.
     * If user presses back, onCleared() flushes immediately.
     */
    private var pendingNotes: String? = null

    fun updateNotes(notes: String) {
        pendingNotes = notes
        notesSaveJob?.cancel()
        notesSaveJob = viewModelScope.launch {
            delay(500) // debounce
            flushNotes()
        }
    }

    private suspend fun flushNotes() {
        pendingNotes?.let { notes ->
            voiceLogRepository.updateNotes(voiceLogId, notes)
            pendingNotes = null
        }
    }

    fun addTextNote(text: String) {
        viewModelScope.launch {
            voiceNoteDao.insert(
                VoiceNoteEntity(
                    id = UUID.randomUUID().toString(),
                    voiceLogId = voiceLogId,
                    textNote = text,
                    createdAt = DateTimeUtil.now()
                )
            )
        }
    }

    fun saveVoiceNote(fileName: String, durationMs: Long, textNote: String?) {
        viewModelScope.launch {
            voiceNoteDao.insert(
                VoiceNoteEntity(
                    id = UUID.randomUUID().toString(),
                    voiceLogId = voiceLogId,
                    audioFileName = fileName,
                    durationMs = durationMs,
                    textNote = textNote?.takeIf { it.isNotBlank() },
                    createdAt = DateTimeUtil.now()
                )
            )
        }
    }

    fun deleteVoiceNote(note: VoiceNote) {
        viewModelScope.launch {
            note.audioFileName?.let { audioFileManager.deleteFile(it) }
            voiceNoteDao.delete(
                VoiceNoteEntity(
                    id = note.id,
                    voiceLogId = note.voiceLogId,
                    audioFileName = note.audioFileName,
                    durationMs = note.durationMs,
                    textNote = note.textNote,
                    createdAt = note.createdAt
                )
            )
        }
    }

    fun deleteLog(voiceLog: VoiceLog, onDeleted: () -> Unit) {
        viewModelScope.launch {
            voiceLogRepository.delete(voiceLog)
            onDeleted()
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.stop()
        // Flush any pending notes immediately before ViewModel dies
        pendingNotes?.let { notes ->
            // Use a non-cancellable scope since viewModelScope is about to be cancelled
            kotlinx.coroutines.runBlocking {
                voiceLogRepository.updateNotes(voiceLogId, notes)
            }
        }
    }
}
