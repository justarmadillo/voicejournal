package com.voicejournal.app.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.voicejournal.app.data.local.db.entity.VoiceLogEntity
import com.voicejournal.app.data.local.db.relation.CategoryCount
import com.voicejournal.app.data.local.db.relation.VoiceLogWithCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceLogDao {

    @Transaction
    @Query("SELECT * FROM voice_logs ORDER BY created_at DESC")
    fun getAllWithCategories(): Flow<List<VoiceLogWithCategories>>

    @Transaction
    @Query("SELECT * FROM voice_logs ORDER BY created_at DESC LIMIT :limit")
    fun getRecentLogs(limit: Int): Flow<List<VoiceLogWithCategories>>

    @Transaction
    @Query("SELECT * FROM voice_logs WHERE id = :id")
    fun getByIdWithCategories(id: String): Flow<VoiceLogWithCategories?>

    @Transaction
    @Query("SELECT * FROM voice_logs WHERE person_id = :personId ORDER BY created_at DESC")
    fun getByPersonId(personId: String): Flow<List<VoiceLogWithCategories>>

    @Query("""
        SELECT c.name as categoryName, c.color_hex as categoryColorHex, COUNT(*) as count
        FROM voice_logs vl
        JOIN voice_log_categories vlc ON vl.id = vlc.voice_log_id
        JOIN categories c ON vlc.category_id = c.id
        WHERE vl.person_id = :personId
        GROUP BY c.id
        ORDER BY count DESC
    """)
    fun getCategoryStatsForPerson(personId: String): Flow<List<CategoryCount>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(voiceLog: VoiceLogEntity)

    @Query("UPDATE voice_logs SET notes = :notes, updated_at = :updatedAt WHERE id = :id")
    suspend fun updateNotes(id: String, notes: String, updatedAt: Long)

    @Query("UPDATE voice_logs SET person_id = :personId, notes = :notes, is_draft = 0, updated_at = :updatedAt WHERE id = :id")
    suspend fun finalizeDraft(id: String, personId: String, notes: String?, updatedAt: Long)

    @Query("DELETE FROM voice_logs WHERE id = :id")
    suspend fun deleteById(id: String)

    @Update
    suspend fun update(voiceLog: VoiceLogEntity)

    @Delete
    suspend fun delete(voiceLog: VoiceLogEntity)

    @Query("DELETE FROM voice_logs")
    suspend fun deleteAll()

    @Query("SELECT * FROM voice_logs")
    suspend fun getAllSync(): List<VoiceLogEntity>
}
