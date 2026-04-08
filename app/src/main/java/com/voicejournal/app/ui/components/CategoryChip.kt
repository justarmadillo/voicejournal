package com.voicejournal.app.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.voicejournal.app.domain.model.Category

@Composable
fun CategoryChip(
    category: Category,
    modifier: Modifier = Modifier
) {
    val chipColor = category.colorHex?.let {
        try { Color(android.graphics.Color.parseColor(it)) }
        catch (_: Exception) { null }
    }

    AssistChip(
        onClick = {},
        label = { Text(category.name, style = MaterialTheme.typography.labelSmall) },
        modifier = modifier.padding(end = 4.dp),
        colors = chipColor?.let {
            AssistChipDefaults.assistChipColors(containerColor = it.copy(alpha = 0.2f))
        } ?: AssistChipDefaults.assistChipColors()
    )
}

@Composable
fun SelectableCategoryChip(
    category: Category,
    selected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chipColor = category.colorHex?.let {
        try { Color(android.graphics.Color.parseColor(it)) }
        catch (_: Exception) { null }
    }

    FilterChip(
        selected = selected,
        onClick = onToggle,
        label = { Text(category.name) },
        modifier = modifier.padding(end = 4.dp),
        colors = chipColor?.let {
            FilterChipDefaults.filterChipColors(
                selectedContainerColor = it.copy(alpha = 0.3f)
            )
        } ?: FilterChipDefaults.filterChipColors()
    )
}
