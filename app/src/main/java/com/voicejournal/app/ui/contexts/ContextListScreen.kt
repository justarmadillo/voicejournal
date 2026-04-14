package com.voicejournal.app.ui.contexts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lightbulb
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
import com.voicejournal.app.data.local.db.dao.ContextWithLogCount
import com.voicejournal.app.ui.components.ConfirmDeleteDialog
import com.voicejournal.app.ui.components.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContextListScreen(
    onNavigateToContextDetail: (contextId: String) -> Unit,
    viewModel: ContextListViewModel = hiltViewModel()
) {
    val contexts by viewModel.contextsWithCount.collectAsStateWithLifecycle()
    var contextToDelete by remember { mutableStateOf<ContextWithLogCount?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Contexts") })

        if (contexts.isEmpty()) {
            EmptyState(
                icon = Icons.Default.Lightbulb,
                title = "No contexts yet",
                subtitle = "Contexts will appear here when you record a voice memo about a situation"
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(contexts, key = { it.id }) { context ->
                    ListItem(
                        headlineContent = { Text(context.name) },
                        supportingContent = {
                            Text(
                                "${context.log_count} recording${if (context.log_count != 1) "s" else ""}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        leadingContent = { Icon(Icons.Default.Lightbulb, null) },
                        trailingContent = {
                            IconButton(onClick = { contextToDelete = context }) {
                                Icon(Icons.Default.Delete, "Delete")
                            }
                        },
                        modifier = Modifier.clickable {
                            onNavigateToContextDetail(context.id)
                        }
                    )
                }
            }
        }
    }

    contextToDelete?.let { context ->
        ConfirmDeleteDialog(
            title = "Delete ${context.name}?",
            message = "This will permanently delete ${context.name} and all ${context.log_count} recording(s).",
            onConfirm = {
                viewModel.deleteContext(context)
                contextToDelete = null
            },
            onDismiss = { contextToDelete = null }
        )
    }
}
