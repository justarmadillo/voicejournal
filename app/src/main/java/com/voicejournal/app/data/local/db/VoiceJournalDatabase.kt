package com.voicejournal.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.voicejournal.app.data.local.db.dao.CategoryDao
import com.voicejournal.app.data.local.db.dao.PersonDao
import com.voicejournal.app.data.local.db.dao.VoiceLogCategoryDao
import com.voicejournal.app.data.local.db.dao.VoiceLogDao
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao
import com.voicejournal.app.data.local.db.entity.CategoryEntity
import com.voicejournal.app.data.local.db.entity.PersonEntity
import com.voicejournal.app.data.local.db.entity.VoiceLogCategoryCrossRef
import com.voicejournal.app.data.local.db.entity.VoiceLogEntity
import com.voicejournal.app.data.local.db.entity.VoiceNoteEntity

@Database(
    entities = [
        PersonEntity::class,
        CategoryEntity::class,
        VoiceLogEntity::class,
        VoiceLogCategoryCrossRef::class,
        VoiceNoteEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class VoiceJournalDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun categoryDao(): CategoryDao
    abstract fun voiceLogDao(): VoiceLogDao
    abstract fun voiceLogCategoryDao(): VoiceLogCategoryDao
    abstract fun voiceNoteDao(): VoiceNoteDao
}
