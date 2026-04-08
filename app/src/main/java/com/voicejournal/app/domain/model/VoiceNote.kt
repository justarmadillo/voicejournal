package com.voicejournal.app.domain.model

data class VoiceNote(
    val id: String,
    val voiceLogId: String,
    val audioFileName: String? = null,
    val durationMs: Long = 0,
    val textNote: String? = null,
    val createdAt: Long
)
