package com.voicejournal.app.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "voice_notes",
    foreignKeys = [
        ForeignKey(
            entity = VoiceLogEntity::class,
            parentColumns = ["id"],
            childColumns = ["voice_log_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["voice_log_id"])]
)
data class VoiceNoteEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "voice_log_id")
    val voiceLogId: String,
    @ColumnInfo(name = "audio_file_name")
    val audioFileName: String? = null,
    @ColumnInfo(name = "duration_ms")
    val durationMs: Long = 0,
    @ColumnInfo(name = "text_note")
    val textNote: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Long
)
