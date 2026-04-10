package com.voicejournal.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voicejournal.app.domain.model.VoiceLog
import com.voicejournal.app.ui.components.CategoryChip
import com.voicejournal.app.ui.components.EmptyState
import com.voicejournal.app.ui.components.RecordButton
import com.voicejournal.app.util.DateTimeUtil
import com.voicejournal.app.util.DurationUtil
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    onNavigateToLogDetail: (voiceLogId: String) -> Unit,
    onNavigateToFinalizeDraft: (draftId: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val recentLogs by viewModel.recentLogs.collectAsStateWithLifecycle()
    val isRecording by viewModel.isRecording.collectAsStateWithLifecycle()
    val draftSaved by viewModel.draftSaved.collectAsStateWithLifecycle()
    var recordingSeconds by remember { mutableLongStateOf(0L) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(isRecording) {
        recordingSeconds = 0
        while (isRecording) {
            delay(1000)
            recordingSeconds++
        }
    }

    LaunchedEffect(draftSaved) {
        if (draftSaved) {
            snackbarHostState.showSnackbar("Saved as draft - tap to finalize")
            viewModel.clearDraftSaved()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(title = { Text("VoiceJournal") })

            // Record button area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RecordButton(
                        isRecording = isRecording,
                        onClick = {
                            if (isRecording) {
                                viewModel.stopAndSaveDraft()
                            } else {
                                viewModel.startRecording()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (isRecording) {
                            "Recording... ${DurationUtil.formatDuration(recordingSeconds * 1000)}"
                        } else {
                            "Tap to record"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Recent logs
            if (recentLogs.isEmpty() && !isRecording) {
                EmptyState(
                    icon = Icons.Default.Mic,
                    title = "No recordings yet",
                    subtitle = "Tap the button above to record your first voice memo"
                )
            } else {
                Text(
                    text = "Recent",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recentLogs, key = { it.id }, contentType = { "voice_log" }) { log ->
                        VoiceLogCard(
                            log = log,
                            onClick = {
                                if (log.isDraft) onNavigateToFinalizeDraft(log.id)
                                else onNavigateToLogDetail(log.id)
                            }
                        )
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun VoiceLogCard(
    log: VoiceLog,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = if (log.isDraft) CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ) else CardDefaults.cardColors()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (log.isDraft) {
                        Text(
                            text = "DRAFT",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(MaterialTheme.colorScheme.tertiary)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Icon(Icons.Default.Edit, "Tap to finalize", modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.tertiary)
                    } else {
                        Text(
                            text = log.personName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                if (log.voiceNoteCount > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.Mic,
                            contentDescription = "${log.voiceNoteCount} notes",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${log.voiceNoteCount}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Text(
                    text = DurationUtil.formatDuration(log.durationMs),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = DateTimeUtil.formatDateTime(log.createdAt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (!log.notes.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = log.notes,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            if (log.categories.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow {
                    log.categories.forEach { cat ->
                        CategoryChip(category = cat)
                    }
                }
            }
        }
    }
}
