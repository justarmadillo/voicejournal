package com.voicejournal.app.data.local.db.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.voicejournal.app.data.local.db.entity.VoiceNoteEntity
import kotlinx.coroutines.flow.Flow

data class VoiceLogNoteCount(
    @ColumnInfo(name = "voice_log_id") val voiceLogId: String,
    val count: Int
)

@Dao
interface VoiceNoteDao {

    @Query("SELECT * FROM voice_notes WHERE voice_log_id = :voiceLogId ORDER BY created_at ASC")
    fun getByVoiceLogId(voiceLogId: String): Flow<List<VoiceNoteEntity>>

    @Query("SELECT voice_log_id, COUNT(*) as count FROM voice_notes GROUP BY voice_log_id")
    fun getAllNoteCounts(): Flow<List<VoiceLogNoteCount>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(voiceNote: VoiceNoteEntity)

    @Delete
    suspend fun delete(voiceNote: VoiceNoteEntity)

    @Query("DELETE FROM voice_notes WHERE voice_log_id = :voiceLogId")
    suspend fun deleteByVoiceLogId(voiceLogId: String)

    @Query("DELETE FROM voice_notes")
    suspend fun deleteAll()

    @Query("SELECT * FROM voice_notes")
    suspend fun getAllSync(): List<VoiceNoteEntity>
}
