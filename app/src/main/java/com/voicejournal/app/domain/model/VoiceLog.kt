package com.voicejournal.app.domain.model

data class VoiceLog(
    val id: String,
    val personId: String? = null,
    val personName: String,
    val audioFileName: String,
    val durationMs: Long,
    val title: String? = null,
    val notes: String? = null,
    val categories: List<Category> = emptyList(),
    val voiceNoteCount: Int = 0,
    val isDraft: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)
