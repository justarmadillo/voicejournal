package com.voicejournal.app.data.local.db.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.voicejournal.app.data.local.db.entity.ContextEntity
import kotlinx.coroutines.flow.Flow

data class ContextWithLogCount(
    val id: String,
    val name: String,
    val notes: String?,
    val created_at: Long,
    val updated_at: Long,
    val log_count: Int
)

@Dao
interface ContextDao {

    @Query("SELECT * FROM contexts ORDER BY name ASC")
    fun getAll(): Flow<List<ContextEntity>>

    @Query("SELECT * FROM contexts WHERE id = :id")
    fun getById(id: String): Flow<ContextEntity?>

    @Query("""
        SELECT c.*, COUNT(vl.id) as log_count
        FROM contexts c
        LEFT JOIN voice_logs vl ON c.id = vl.context_id
        GROUP BY c.id
        ORDER BY c.name ASC
    """)
    fun getAllWithLogCount(): Flow<List<ContextWithLogCount>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(context: ContextEntity)

    @Update
    suspend fun update(context: ContextEntity)

    @Delete
    suspend fun delete(context: ContextEntity)

    @Query("DELETE FROM contexts")
    suspend fun deleteAll()

    @Query("SELECT * FROM contexts")
    suspend fun getAllSync(): List<ContextEntity>
}
