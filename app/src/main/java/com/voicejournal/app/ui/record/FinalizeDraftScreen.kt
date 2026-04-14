package com.voicejournal.app.ui.record

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.voicejournal.app.ui.components.AudioPlayerBar
import com.voicejournal.app.ui.components.ConfirmDeleteDialog
import com.voicejournal.app.ui.components.SelectableCategoryChip
import com.voicejournal.app.util.DurationUtil

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FinalizeDraftScreen(
    onDone: () -> Unit,
    onBack: () -> Unit,
    viewModel: FinalizeDraftViewModel = hiltViewModel()
) {
    val draft by viewModel.draft.collectAsStateWithLifecycle()
    val playbackState by viewModel.audioPlayer.playbackState.collectAsStateWithLifecycle()
    val persons by viewModel.persons.collectAsStateWithLifecycle()
    val contexts by viewModel.contexts.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val subjectMode by viewModel.subjectMode.collectAsStateWithLifecycle()
    val selectedPerson by viewModel.selectedPerson.collectAsStateWithLifecycle()
    val selectedContext by viewModel.selectedContext.collectAsStateWithLifecycle()
    val selectedCategories by viewModel.selectedCategories.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val notes by viewModel.notes.collectAsStateWithLifecycle()

    var showAddCategoryDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val canSave = when (subjectMode) {
        SubjectMode.PERSON -> selectedPerson != null
        SubjectMode.CONTEXT -> selectedContext != null
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Finalize Draft") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
            },
            actions = {
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, "Delete draft", tint = MaterialTheme.colorScheme.error)
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Draft info with audio player
            draft?.let { d ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Draft recording: ${DurationUtil.formatDuration(d.durationMs)}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        AudioPlayerBar(
                            playbackState = if (playbackState.currentFileName == d.audioFileName) playbackState else com.voicejournal.app.audio.PlaybackState(),
                            onPlay = { viewModel.playAudio(d.audioFileName) },
                            onPause = { viewModel.pauseAudio() },
                            onStop = { viewModel.stopAudio() }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Subject mode toggle: Person or Context
            Text(text = "What is this about?", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                FilterChip(
                    selected = subjectMode == SubjectMode.PERSON,
                    onClick = { viewModel.setSubjectMode(SubjectMode.PERSON) },
                    label = { Text("Person") },
                    leadingIcon = { Icon(Icons.Default.Person, null, modifier = Modifier.height(18.dp)) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilterChip(
                    selected = subjectMode == SubjectMode.CONTEXT,
                    onClick = { viewModel.setSubjectMode(SubjectMode.CONTEXT) },
                    label = { Text("Context") },
                    leadingIcon = { Icon(Icons.Default.Lightbulb, null, modifier = Modifier.height(18.dp)) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                placeholder = {
                    Text(if (subjectMode == SubjectMode.PERSON) "Search or add person..." else "Search or add context...")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Person or Context list
            if (subjectMode == SubjectMode.PERSON) {
                val filtered = if (searchQuery.isBlank()) persons
                else persons.filter { it.name.contains(searchQuery, ignoreCase = true) }

                LazyColumn(modifier = Modifier.fillMaxWidth().height(130.dp)) {
                    if (searchQuery.isNotBlank() && filtered.none { it.name.equals(searchQuery, ignoreCase = true) }) {
                        item {
                            ListItem(
                                headlineContent = { Text("Add \"$searchQuery\"") },
                                leadingContent = { Icon(Icons.Default.Add, null) },
                                modifier = Modifier.clickable {
                                    viewModel.createPerson(searchQuery)
                                    viewModel.updateSearchQuery("")
                                }
                            )
                        }
                    }
                    items(filtered, key = { it.id }) { person ->
                        val isSelected = selectedPerson?.id == person.id
                        ListItem(
                            headlineContent = { Text(person.name) },
                            leadingContent = { Icon(Icons.Default.Person, null) },
                            trailingContent = { if (isSelected) Icon(Icons.Default.Check, "Selected") },
                            modifier = Modifier.clickable { viewModel.selectPerson(person) },
                            tonalElevation = if (isSelected) 4.dp else 0.dp
                        )
                    }
                }
            } else {
                val filtered = if (searchQuery.isBlank()) contexts
                else contexts.filter { it.name.contains(searchQuery, ignoreCase = true) }

                LazyColumn(modifier = Modifier.fillMaxWidth().height(130.dp)) {
                    if (searchQuery.isNotBlank() && filtered.none { it.name.equals(searchQuery, ignoreCase = true) }) {
                        item {
                            ListItem(
                                headlineContent = { Text("Add \"$searchQuery\"") },
                                leadingContent = { Icon(Icons.Default.Add, null) },
                                modifier = Modifier.clickable {
                                    viewModel.createContext(searchQuery)
                                    viewModel.updateSearchQuery("")
                                }
                            )
                        }
                    }
                    items(filtered, key = { it.id }) { ctx ->
                        val isSelected = selectedContext?.id == ctx.id
                        ListItem(
                            headlineContent = { Text(ctx.name) },
                            leadingContent = { Icon(Icons.Default.Lightbulb, null) },
                            trailingContent = { if (isSelected) Icon(Icons.Default.Check, "Selected") },
                            modifier = Modifier.clickable { viewModel.selectContext(ctx) },
                            tonalElevation = if (isSelected) 4.dp else 0.dp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Categories
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Categories (optional)", style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = { showAddCategoryDialog = true }) {
                    Icon(Icons.Default.Add, null)
                    Text("New")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                categories.forEach { category ->
                    SelectableCategoryChip(
                        category = category,
                        selected = selectedCategories.contains(category.id),
                        onToggle = { viewModel.toggleCategory(category.id) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Notes
            Text("Notes (optional)", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = notes,
                onValueChange = { viewModel.updateNotes(it) },
                placeholder = { Text("Quick summary of what happened...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.finalize(onDone) },
                modifier = Modifier.fillMaxWidth(),
                enabled = canSave
            ) {
                Text("Save")
            }
        }
    }

    if (showAddCategoryDialog) {
        var newCatName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddCategoryDialog = false },
            title = { Text("New Category") },
            text = {
                OutlinedTextField(
                    value = newCatName,
                    onValueChange = { newCatName = it },
                    placeholder = { Text("e.g. helped, lied, kind...") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newCatName.isNotBlank()) {
                        viewModel.createCategory(newCatName.trim())
                        showAddCategoryDialog = false
                    }
                }) { Text("Add") }
            },
            dismissButton = {
                TextButton(onClick = { showAddCategoryDialog = false }) { Text("Cancel") }
            }
        )
    }

    if (showDeleteDialog) {
        ConfirmDeleteDialog(
            title = "Delete draft?",
            message = "This draft recording will be permanently deleted.",
            onConfirm = {
                viewModel.deleteDraft(onDone)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}
