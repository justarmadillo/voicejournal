package com.voicejournal.app.domain.model

data class VoiceContext(
    val id: String,
    val name: String,
    val notes: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)
