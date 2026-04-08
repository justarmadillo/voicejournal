package com.voicejournal.app.ui.people

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicejournal.app.audio.AudioPlayer
import com.voicejournal.app.data.local.db.relation.CategoryCount
import com.voicejournal.app.data.repository.PersonRepository
import com.voicejournal.app.data.repository.VoiceLogRepository
import com.voicejournal.app.domain.model.Person
import com.voicejournal.app.domain.model.VoiceLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val personRepository: PersonRepository,
    private val voiceLogRepository: VoiceLogRepository,
    val audioPlayer: AudioPlayer,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val personId: String = savedStateHandle["personId"] ?: ""

    val person: StateFlow<Person?> = personRepository.getById(personId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val voiceLogs: StateFlow<List<VoiceLog>> = voiceLogRepository.getByPersonId(personId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categoryStats: StateFlow<List<CategoryCount>> = voiceLogRepository.getCategoryStatsForPerson(personId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun playAudio(fileName: String) {
        audioPlayer.play(fileName)
    }

    fun pauseAudio() {
        audioPlayer.pause()
    }

    fun stopAudio() {
        audioPlayer.stop()
    }

    fun deleteLog(voiceLog: VoiceLog) {
        viewModelScope.launch {
            voiceLogRepository.delete(voiceLog)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.stop()
    }
}
