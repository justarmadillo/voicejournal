package com.voicejournal.app.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicejournal.app.data.repository.VoiceLogRepository
import com.voicejournal.app.domain.model.VoiceLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    voiceLogRepository: VoiceLogRepository
) : ViewModel() {

    private val allLogs = voiceLogRepository.getRecentLogs(500)

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    val results: StateFlow<List<VoiceLog>> = combine(allLogs, _query) { logs, q ->
        if (q.isBlank()) return@combine emptyList()
        val words = q.lowercase().split("\\s+".toRegex()).filter { it.isNotBlank() }
        if (words.isEmpty()) return@combine emptyList()
        logs.filter { log ->
            val searchable = buildString {
                append(log.notes.orEmpty().lowercase())
                append(" ")
                append(log.subjectName.lowercase())
            }
            words.all { word -> searchable.contains(word) }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateQuery(query: String) {
        _query.value = query
    }
}
