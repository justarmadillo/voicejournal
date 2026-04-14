package com.voicejournal.app.ui.contexts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicejournal.app.data.local.db.dao.ContextWithLogCount
import com.voicejournal.app.data.local.db.dao.ContextDao
import com.voicejournal.app.data.repository.ContextRepository
import com.voicejournal.app.domain.model.VoiceContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContextListViewModel @Inject constructor(
    private val contextDao: ContextDao,
    private val contextRepository: ContextRepository
) : ViewModel() {

    val contextsWithCount: StateFlow<List<ContextWithLogCount>> = contextDao.getAllWithLogCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteContext(context: ContextWithLogCount) {
        viewModelScope.launch {
            contextRepository.delete(
                VoiceContext(
                    id = context.id,
                    name = context.name,
                    notes = context.notes,
                    createdAt = context.created_at,
                    updatedAt = context.updated_at
                )
            )
        }
    }
}
