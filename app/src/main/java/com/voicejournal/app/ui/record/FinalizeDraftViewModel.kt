package com.voicejournal.app.ui.record

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicejournal.app.audio.AudioPlayer
import com.voicejournal.app.data.repository.CategoryRepository
import com.voicejournal.app.data.repository.ContextRepository
import com.voicejournal.app.data.repository.PersonRepository
import com.voicejournal.app.data.repository.VoiceLogRepository
import com.voicejournal.app.domain.model.Category
import com.voicejournal.app.domain.model.Person
import com.voicejournal.app.domain.model.VoiceContext
import com.voicejournal.app.domain.model.VoiceLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SubjectMode { PERSON, CONTEXT }

@HiltViewModel
class FinalizeDraftViewModel @Inject constructor(
    private val personRepository: PersonRepository,
    private val categoryRepository: CategoryRepository,
    private val contextRepository: ContextRepository,
    private val voiceLogRepository: VoiceLogRepository,
    val audioPlayer: AudioPlayer,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val draftId: String = savedStateHandle["draftId"] ?: ""

    val draft: StateFlow<VoiceLog?> = voiceLogRepository.getByIdWithCategories(draftId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val persons: StateFlow<List<Person>> = personRepository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val contexts: StateFlow<List<VoiceContext>> = contextRepository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories: StateFlow<List<Category>> = categoryRepository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _subjectMode = MutableStateFlow(SubjectMode.PERSON)
    val subjectMode: StateFlow<SubjectMode> = _subjectMode.asStateFlow()

    private val _selectedPerson = MutableStateFlow<Person?>(null)
    val selectedPerson: StateFlow<Person?> = _selectedPerson.asStateFlow()

    private val _selectedContext = MutableStateFlow<VoiceContext?>(null)
    val selectedContext: StateFlow<VoiceContext?> = _selectedContext.asStateFlow()

    private val _selectedCategories = MutableStateFlow<Set<String>>(emptySet())
    val selectedCategories: StateFlow<Set<String>> = _selectedCategories.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _notes = MutableStateFlow("")
    val notes: StateFlow<String> = _notes.asStateFlow()

    val canSave: Boolean get() = when (_subjectMode.value) {
        SubjectMode.PERSON -> _selectedPerson.value != null
        SubjectMode.CONTEXT -> _selectedContext.value != null
    }

    fun setSubjectMode(mode: SubjectMode) {
        _subjectMode.value = mode
        _searchQuery.value = ""
    }

    fun selectPerson(person: Person) {
        _selectedPerson.value = person
        _selectedContext.value = null
    }

    fun selectContext(context: VoiceContext) {
        _selectedContext.value = context
        _selectedPerson.value = null
    }

    fun toggleCategory(categoryId: String) {
        _selectedCategories.value = _selectedCategories.value.toMutableSet().apply {
            if (contains(categoryId)) remove(categoryId) else add(categoryId)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateNotes(notes: String) {
        _notes.value = notes
    }

    fun createPerson(name: String) {
        viewModelScope.launch {
            val person = personRepository.create(name)
            _selectedPerson.value = person
            _selectedContext.value = null
        }
    }

    fun createContext(name: String) {
        viewModelScope.launch {
            val context = contextRepository.create(name)
            _selectedContext.value = context
            _selectedPerson.value = null
        }
    }

    fun createCategory(name: String) {
        viewModelScope.launch {
            val category = categoryRepository.create(name)
            _selectedCategories.value = _selectedCategories.value + category.id
        }
    }

    fun finalize(onDone: () -> Unit) {
        val personId = _selectedPerson.value?.id
        val contextId = _selectedContext.value?.id
        if (personId == null && contextId == null) return
        viewModelScope.launch {
            voiceLogRepository.finalizeDraft(
                draftId = draftId,
                personId = personId,
                contextId = contextId,
                categoryIds = _selectedCategories.value.toList(),
                notes = _notes.value.takeIf { it.isNotBlank() }
            )
            onDone()
        }
    }

    fun playAudio(fileName: String) { audioPlayer.play(fileName) }
    fun pauseAudio() { audioPlayer.pause() }
    fun stopAudio() { audioPlayer.stop() }

    fun deleteDraft(onDone: () -> Unit) {
        viewModelScope.launch {
            draft.value?.let { voiceLogRepository.delete(it) }
            onDone()
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioPlayer.stop()
    }
}
