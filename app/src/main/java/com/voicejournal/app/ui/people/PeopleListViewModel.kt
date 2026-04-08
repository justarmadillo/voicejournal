package com.voicejournal.app.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voicejournal.app.data.local.db.dao.PersonWithLogCount
import com.voicejournal.app.data.local.db.dao.PersonDao
import com.voicejournal.app.data.repository.PersonRepository
import com.voicejournal.app.domain.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleListViewModel @Inject constructor(
    private val personDao: PersonDao,
    private val personRepository: PersonRepository
) : ViewModel() {

    val personsWithCount: StateFlow<List<PersonWithLogCount>> = personDao.getAllWithLogCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deletePerson(person: PersonWithLogCount) {
        viewModelScope.launch {
            personRepository.delete(
                Person(
                    id = person.id,
                    name = person.name,
                    notes = person.notes,
                    createdAt = person.created_at,
                    updatedAt = person.updated_at
                )
            )
        }
    }
}
