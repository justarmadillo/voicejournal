package com.voicejournal.app.domain.model

data class Category(
    val id: String,
    val name: String,
    val colorHex: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)
