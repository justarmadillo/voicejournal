package com.voicejournal.app.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "voice_log_categories",
    primaryKeys = ["voice_log_id", "category_id"],
    foreignKeys = [
        ForeignKey(
            entity = VoiceLogEntity::class,
            parentColumns = ["id"],
            childColumns = ["voice_log_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["category_id"])]
)
data class VoiceLogCategoryCrossRef(
    @ColumnInfo(name = "voice_log_id")
    val voiceLogId: String,
    @ColumnInfo(name = "category_id")
    val categoryId: String
)
