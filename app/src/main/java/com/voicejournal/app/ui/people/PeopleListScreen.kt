package com.voicejournal.app.ui.people

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voicejournal.app.data.local.db.dao.PersonWithLogCount
import com.voicejournal.app.ui.components.ConfirmDeleteDialog
import com.voicejournal.app.ui.components.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleListScreen(
    onNavigateToPersonDetail: (personId: String) -> Unit,
    viewModel: PeopleListViewModel = hiltViewModel()
) {
    val persons by viewModel.personsWithCount.collectAsStateWithLifecycle()
    var personToDelete by remember { mutableStateOf<PersonWithLogCount?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("People") })

        if (persons.isEmpty()) {
            EmptyState(
                icon = Icons.Default.People,
                title = "No people yet",
                subtitle = "People will appear here when you record a voice memo about them"
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(persons, key = { it.id }) { person ->
                    ListItem(
                        headlineContent = { Text(person.name) },
                        supportingContent = {
                            Text(
                                "${person.log_count} recording${if (person.log_count != 1) "s" else ""}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        leadingContent = { Icon(Icons.Default.Person, null) },
                        trailingContent = {
                            IconButton(onClick = { personToDelete = person }) {
                                Icon(Icons.Default.Delete, "Delete")
                            }
                        },
                        modifier = Modifier.clickable {
                            onNavigateToPersonDetail(person.id)
                        }
                    )
                }
            }
        }
    }

    personToDelete?.let { person ->
        ConfirmDeleteDialog(
            title = "Delete ${person.name}?",
            message = "This will permanently delete ${person.name} and all ${person.log_count} recording(s).",
            onConfirm = {
                viewModel.deletePerson(person)
                personToDelete = null
            },
            onDismiss = { personToDelete = null }
        )
    }
}
