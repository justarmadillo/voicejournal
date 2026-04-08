package com.voicejournal.app.data.local.db.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.voicejournal.app.data.local.db.entity.CategoryEntity
import com.voicejournal.app.data.local.db.entity.VoiceLogCategoryCrossRef
import com.voicejournal.app.data.local.db.entity.VoiceLogEntity

data class VoiceLogWithCategories(
    @Embedded val voiceLog: VoiceLogEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = VoiceLogCategoryCrossRef::class,
            parentColumn = "voice_log_id",
            entityColumn = "category_id"
        )
    )
    val categories: List<CategoryEntity>
)
