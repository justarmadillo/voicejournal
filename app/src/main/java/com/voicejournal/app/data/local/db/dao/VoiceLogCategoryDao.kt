package com.voicejournal.app.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.voicejournal.app.data.local.db.entity.VoiceLogCategoryCrossRef

@Dao
interface VoiceLogCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crossRef: VoiceLogCategoryCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<VoiceLogCategoryCrossRef>)

    @Query("DELETE FROM voice_log_categories WHERE voice_log_id = :voiceLogId")
    suspend fun deleteByVoiceLogId(voiceLogId: String)

    @Query("DELETE FROM voice_log_categories")
    suspend fun deleteAll()

    @Query("SELECT * FROM voice_log_categories")
    suspend fun getAllSync(): List<VoiceLogCategoryCrossRef>
}
