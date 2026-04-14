package com.voicejournal.app.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "voice_logs",
    foreignKeys = [
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = ["id"],
            childColumns = ["person_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ContextEntity::class,
            parentColumns = ["id"],
            childColumns = ["context_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["person_id"]),
        Index(value = ["context_id"]),
        Index(value = ["created_at"])
    ]
)
data class VoiceLogEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "person_id")
    val personId: String? = null,
    @ColumnInfo(name = "context_id")
    val contextId: String? = null,
    @ColumnInfo(name = "audio_file_name")
    val audioFileName: String,
    @ColumnInfo(name = "duration_ms")
    val durationMs: Long,
    val title: String? = null,
    val notes: String? = null,
    @ColumnInfo(name = "is_draft", defaultValue = "0")
    val isDraft: Boolean = false,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
)
