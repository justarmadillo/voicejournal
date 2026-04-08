package com.voicejournal.app.ui.people

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.voicejournal.app.domain.model.VoiceLog
import com.voicejournal.app.ui.components.AudioPlayerBar
import com.voicejournal.app.ui.components.CategoryBreakdownChart
import com.voicejournal.app.ui.components.CategoryChip
import com.voicejournal.app.ui.components.ConfirmDeleteDialog
import com.voicejournal.app.ui.components.EmptyState
import com.voicejournal.app.util.DateTimeUtil
import com.voicejournal.app.util.DurationUtil

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PersonDetailScreen(
    onBack: () -> Unit,
    onNavigateToLogDetail: (voiceLogId: String) -> Unit,
    viewModel: PersonDetailViewModel = hiltViewModel()
) {
    val person by viewModel.person.collectAsStateWithLifecycle()
    val voiceLogs by viewModel.voiceLogs.collectAsStateWithLifecycle()
    val categoryStats by viewModel.categoryStats.collectAsStateWithLifecycle()
    val playbackState by viewModel.audioPlayer.playbackState.collectAsStateWithLifecycle()
    var logToDelete by remember { mutableStateOf<VoiceLog?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(person?.name ?: "Person") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Visual category breakdown at top
            if (categoryStats.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Behavior Pattern",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            CategoryBreakdownChart(stats = categoryStats)
                        }
                    }
                }
            }

            // Total count
            item {
                Text(
                    text = "${voiceLogs.size} recording${if (voiceLogs.size != 1) "s" else ""}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            if (voiceLogs.isEmpty()) {
                item {
                    EmptyState(
                        icon = Icons.Default.History,
                        title = "No recordings",
                        subtitle = "Record a voice memo about this person to see it here"
                    )
                }
            }

            // Timeline grouped by month
            val groupedByMonth = voiceLogs.groupBy { DateTimeUtil.getMonthYearKey(it.createdAt) }

            groupedByMonth.forEach { (_, logsInMonth) ->
                // Month header
                item {
                    Text(
                        text = DateTimeUtil.formatMonthYear(logsInMonth.first().createdAt),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                // Logs in this month
                items(logsInMonth, key = { it.id }) { log ->
                    TimelineLogCard(
                        log = log,
                        isPlaying = playbackState.currentFileName == log.audioFileName && playbackState.isPlaying,
                        onPlay = { viewModel.playAudio(log.audioFileName) },
                        onPause = { viewModel.pauseAudio() },
                        onStop = { viewModel.stopAudio() },
                        onDelete = { logToDelete = log },
                        onClick = { onNavigateToLogDetail(log.id) },
                        playbackState = playbackState
                    )
                }
            }
        }
    }

    logToDelete?.let { log ->
        ConfirmDeleteDialog(
            title = "Delete recording?",
            message = "This will permanently delete this recording from ${DateTimeUtil.formatDate(log.createdAt)}.",
            onConfirm = {
                viewModel.deleteLog(log)
                logToDelete = null
            },
            onDismiss = { logToDelete = null }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TimelineLogCard(
    log: VoiceLog,
    isPlaying: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    playbackState: com.voicejournal.app.audio.PlaybackState
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = DateTimeUtil.formatDate(log.createdAt),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${DateTimeUtil.formatTime(log.createdAt)} - ${DurationUtil.formatDuration(log.durationMs)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (log.voiceNoteCount > 0) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                Icons.Default.Mic,
                                contentDescription = "${log.voiceNoteCount} reflections",
                                modifier = Modifier.size(12.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = " ${log.voiceNoteCount} note${if (log.voiceNoteCount > 1) "s" else ""}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }

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

            // Inline player if this log is playing
            if (playbackState.currentFileName == log.audioFileName) {
                Spacer(modifier = Modifier.height(8.dp))
                AudioPlayerBar(
                    playbackState = playbackState,
                    onPlay = onPlay,
                    onPause = onPause,
                    onStop = onStop
                )
            }
        }
    }
}
