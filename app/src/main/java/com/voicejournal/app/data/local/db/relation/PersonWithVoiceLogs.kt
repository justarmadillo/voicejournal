package com.voicejournal.app.data.local.db.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.voicejournal.app.data.local.db.entity.PersonEntity
import com.voicejournal.app.data.local.db.entity.VoiceLogEntity

data class PersonWithVoiceLogs(
    @Embedded val person: PersonEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "person_id"
    )
    val voiceLogs: List<VoiceLogEntity>
)
