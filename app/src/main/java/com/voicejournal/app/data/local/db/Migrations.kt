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

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Recreate voice_logs with nullable person_id and is_draft column
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS voice_logs_new (
                id TEXT NOT NULL PRIMARY KEY,
                person_id TEXT,
                audio_file_name TEXT NOT NULL,
                duration_ms INTEGER NOT NULL,
                title TEXT,
                notes TEXT,
                is_draft INTEGER NOT NULL DEFAULT 0,
                created_at INTEGER NOT NULL,
                updated_at INTEGER NOT NULL,
                FOREIGN KEY(person_id) REFERENCES persons(id) ON DELETE CASCADE
            )
        """)
        db.execSQL("""
            INSERT INTO voice_logs_new (id, person_id, audio_file_name, duration_ms, title, notes, is_draft, created_at, updated_at)
            SELECT id, person_id, audio_file_name, duration_ms, title, notes, 0, created_at, updated_at FROM voice_logs
        """)
        db.execSQL("DROP TABLE voice_logs")
        db.execSQL("ALTER TABLE voice_logs_new RENAME TO voice_logs")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_voice_logs_person_id ON voice_logs(person_id)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_voice_logs_created_at ON voice_logs(created_at)")
    }
}

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
