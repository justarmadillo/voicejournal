package com.voicejournal.app.ui.search

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.voicejournal.app.util.DateTimeUtil
import com.voicejournal.app.util.DurationUtil

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    onNavigateToLogDetail: (voiceLogId: String) -> Unit,
    onNavigateToFinalizeDraft: (draftId: String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val results by viewModel.results.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Search") })

        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.updateQuery(it) },
            placeholder = { Text("Search notes, people...") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            singleLine = true
        )

        if (query.isBlank()) {
            EmptyState(
                icon = Icons.Default.Search,
                title = "Search your recordings",
                subtitle = "Type keywords to find recordings by notes or person name"
            )
        } else if (results.isEmpty()) {
            EmptyState(
                icon = Icons.Default.Search,
                title = "No results",
                subtitle = "No recordings match \"$query\""
            )
        } else {
            Text(
                text = "${results.size} result${if (results.size != 1) "s" else ""}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(results, key = { it.id }, contentType = { "search_result" }) { log ->
                    SearchResultCard(
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
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SearchResultCard(log: VoiceLog, onClick: () -> Unit) {
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
                        Icon(Icons.Default.Edit, null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.tertiary)
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
                        Icon(Icons.Default.Mic, null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                        Text("${log.voiceNoteCount}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
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
                    maxLines = 3,
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
