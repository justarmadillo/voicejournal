package com.voicejournal.app.ui.logdetail

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voicejournal.app.domain.model.VoiceNote
import com.voicejournal.app.ui.components.AudioPlayerBar
import com.voicejournal.app.ui.components.ConfirmDeleteDialog
import com.voicejournal.app.ui.components.SelectableCategoryChip
import com.voicejournal.app.ui.theme.RecordRed
import com.voicejournal.app.util.DateTimeUtil
import com.voicejournal.app.util.DurationUtil
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LogDetailScreen(
    onBack: () -> Unit,
    viewModel: LogDetailViewModel = hiltViewModel()
) {
    val voiceLog by viewModel.voiceLog.collectAsStateWithLifecycle()
    val allCategories by viewModel.allCategories.collectAsStateWithLifecycle()
    val voiceNotes by viewModel.voiceNotes.collectAsStateWithLifecycle()
    val playbackState by viewModel.audioPlayer.playbackState.collectAsStateWithLifecycle()
    val isRecordingNote by viewModel.audioRecorder.isRecording.collectAsStateWithLifecycle()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showAddNoteDialog by remember { mutableStateOf(false) }
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<VoiceNote?>(null) }

    // Track whether we've loaded notes from DB yet (separate from the text itself)
    var hasLoadedNotes by remember { mutableStateOf(false) }
    var editingNotes by remember { mutableStateOf("") }

    // Voice note recording state
    var recordingFileName by remember { mutableStateOf<String?>(null) }
    var recordingSeconds by remember { mutableLongStateOf(0L) }

    LaunchedEffect(isRecordingNote) {
        recordingSeconds = 0
        while (isRecordingNote) {
            delay(1000)
            recordingSeconds++
        }
    }

    // Load notes from DB exactly once when voiceLog first arrives (not null)
    val currentLog = voiceLog
    LaunchedEffect(currentLog) {
        if (!hasLoadedNotes && currentLog != null) {
            editingNotes = currentLog.notes ?: ""
            hasLoadedNotes = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Recording Detail") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
            },
            actions = {
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }
        )

        voiceLog?.let { log ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header: person + date
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(log.personName, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(DateTimeUtil.formatDateTime(log.createdAt), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Duration: ${DurationUtil.formatDuration(log.durationMs)}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                // Audio player (only show active state when the main recording is playing)
                item {
                    val mainPlaybackState = if (playbackState.currentFileName == log.audioFileName) playbackState else com.voicejournal.app.audio.PlaybackState()
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                        AudioPlayerBar(
                            playbackState = mainPlaybackState,
                            onPlay = { viewModel.playAudio(log.audioFileName) },
                            onPause = { viewModel.pauseAudio() },
                            onStop = { viewModel.stopAudio() },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                // Notes (editable text)
                item {
                    Text("Notes", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = editingNotes,
                        onValueChange = { newText ->
                            editingNotes = newText
                            viewModel.updateNotes(newText)
                        },
                        placeholder = { Text("Add a summary so you know what this recording contains...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 6
                    )
                }

                // Categories (editable + can create new)
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Categories", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        TextButton(onClick = { showAddCategoryDialog = true }) {
                            Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp))
                            Text("New")
                        }
                    }
                    val currentCategoryIds = log.categories.map { it.id }.toSet()
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        allCategories.forEach { category ->
                            SelectableCategoryChip(
                                category = category,
                                selected = currentCategoryIds.contains(category.id),
                                onToggle = {
                                    val newIds = if (currentCategoryIds.contains(category.id)) {
                                        currentCategoryIds - category.id
                                    } else {
                                        currentCategoryIds + category.id
                                    }
                                    viewModel.updateCategories(newIds.toList())
                                }
                            )
                        }
                    }
                }

                // Voice Notes / Reflections
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Reflections", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Row {
                            // Record voice note button
                            if (isRecordingNote) {
                                IconButton(onClick = {
                                    val (fileName, durationMs) = viewModel.audioRecorder.stopRecording()
                                    // Show dialog to add optional text to the voice note
                                    recordingFileName = fileName
                                    showAddNoteDialog = true
                                }) {
                                    Icon(Icons.Default.Stop, "Stop", tint = RecordRed, modifier = Modifier.size(28.dp))
                                }
                            } else {
                                IconButton(onClick = {
                                    viewModel.audioRecorder.startRecording()
                                }) {
                                    Icon(Icons.Default.Mic, "Record voice note", tint = RecordRed, modifier = Modifier.size(28.dp))
                                }
                            }
                            // Text note button
                            IconButton(onClick = {
                                recordingFileName = null
                                showAddNoteDialog = true
                            }) {
                                Icon(Icons.Default.Add, "Add text note")
                            }
                        }
                    }

                    if (isRecordingNote) {
                        Text(
                            text = "Recording note... ${DurationUtil.formatDuration(recordingSeconds * 1000)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = RecordRed
                        )
                    }
                }

                if (voiceNotes.isEmpty()) {
                    item {
                        Text(
                            "No reflections yet. Tap the mic to add a voice reflection, or + for a text note.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                items(voiceNotes, key = { it.id }) { note ->
                    VoiceNoteCard(
                        note = note,
                        playbackState = playbackState,
                        onPlay = { note.audioFileName?.let { viewModel.playAudio(it) } },
                        onPause = { viewModel.pauseAudio() },
                        onStop = { viewModel.stopAudio() },
                        onDelete = { noteToDelete = note }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }

    // Add note dialog (text only, or text attached to voice recording)
    if (showAddNoteDialog) {
        var noteText by remember { mutableStateOf("") }
        val hasVoice = recordingFileName != null

        AlertDialog(
            onDismissRequest = {
                // If we recorded audio but cancel, clean it up
                if (hasVoice) {
                    recordingFileName?.let {
                        viewModel.audioRecorder.cancelRecording()
                    }
                }
                showAddNoteDialog = false
                recordingFileName = null
            },
            title = { Text(if (hasVoice) "Voice Note" else "Text Note") },
            text = {
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    placeholder = {
                        Text(if (hasVoice) "Optional text to go with voice note..." else "Your reflection or thought...")
                    },
                    minLines = 2,
                    maxLines = 4,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (hasVoice) {
                        viewModel.saveVoiceNote(
                            fileName = recordingFileName!!,
                            durationMs = recordingSeconds * 1000,
                            textNote = noteText.takeIf { it.isNotBlank() }
                        )
                    } else if (noteText.isNotBlank()) {
                        viewModel.addTextNote(noteText.trim())
                    }
                    showAddNoteDialog = false
                    recordingFileName = null
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showAddNoteDialog = false
                    recordingFileName = null
                }) { Text("Cancel") }
            }
        )
    }

    // Delete recording dialog
    if (showDeleteDialog) {
        voiceLog?.let { log ->
            ConfirmDeleteDialog(
                title = "Delete recording?",
                message = "This will permanently delete this recording, all notes, and its audio files.",
                onConfirm = {
                    viewModel.deleteLog(log) { onBack() }
                    showDeleteDialog = false
                },
                onDismiss = { showDeleteDialog = false }
            )
        }
    }

    // Delete note dialog
    noteToDelete?.let { note ->
        ConfirmDeleteDialog(
            title = "Delete note?",
            message = "This reflection will be permanently deleted.",
            onConfirm = {
                viewModel.deleteVoiceNote(note)
                noteToDelete = null
            },
            onDismiss = { noteToDelete = null }
        )
    }

    // Add category dialog
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
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
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
}

@Composable
private fun VoiceNoteCard(
    note: VoiceNote,
    playbackState: com.voicejournal.app.audio.PlaybackState,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = DateTimeUtil.formatDateTime(note.createdAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Delete, "Delete", modifier = Modifier.size(16.dp))
                }
            }

            // Text note
            note.textNote?.let { text ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(text, style = MaterialTheme.typography.bodyMedium)
            }

            // Audio player for voice note
            note.audioFileName?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Voice note - ${DurationUtil.formatDuration(note.durationMs)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (playbackState.currentFileName == it) {
                    AudioPlayerBar(
                        playbackState = playbackState,
                        onPlay = onPlay,
                        onPause = onPause,
                        onStop = onStop
                    )
                } else {
                    OutlinedButton(onClick = onPlay, modifier = Modifier.padding(top = 4.dp)) {
                        Icon(Icons.Default.Mic, null, modifier = Modifier.size(16.dp))
                        Text("  Play", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
