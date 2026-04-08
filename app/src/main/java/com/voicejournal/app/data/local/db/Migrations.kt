package com.voicejournal.app.data.local.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * All database migrations live here.
 * Each migration must be registered in AppModule.provideDatabase().
 *
 * IMPORTANT: When adding a new migration:
 * 1. Bump the @Database version number in VoiceJournalDatabase.kt
 * 2. Create a new MIGRATION_X_Y object here
 * 3. Add it to AppModule: .addMigrations(MIGRATION_1_2, MIGRATION_2_3, ...)
 * 4. fallbackToDestructiveMigration() is the safety net if a migration is missed
 */

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add voice_notes table for reflections on recordings
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS voice_notes (
                id TEXT NOT NULL PRIMARY KEY,
                voice_log_id TEXT NOT NULL,
                audio_file_name TEXT,
                duration_ms INTEGER NOT NULL DEFAULT 0,
                text_note TEXT,
                created_at INTEGER NOT NULL,
                FOREIGN KEY(voice_log_id) REFERENCES voice_logs(id) ON DELETE CASCADE
            )
        """)
        db.execSQL("CREATE INDEX IF NOT EXISTS index_voice_notes_voice_log_id ON voice_notes(voice_log_id)")
    }
}
