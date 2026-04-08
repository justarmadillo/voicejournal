package com.voicejournal.app.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.voicejournal.app.data.local.db.entity.PersonEntity
import com.voicejournal.app.data.local.db.relation.PersonWithVoiceLogs
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM persons ORDER BY name ASC")
    fun getAll(): Flow<List<PersonEntity>>

    @Query("SELECT * FROM persons WHERE id = :id")
    fun getById(id: String): Flow<PersonEntity?>

    @Transaction
    @Query("SELECT * FROM persons WHERE id = :id")
    fun getPersonWithVoiceLogs(id: String): Flow<PersonWithVoiceLogs?>

    @Query("SELECT * FROM persons WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchByName(query: String): Flow<List<PersonEntity>>

    @Query("""
        SELECT p.*, COUNT(vl.id) as log_count
        FROM persons p
        LEFT JOIN voice_logs vl ON p.id = vl.person_id
        GROUP BY p.id
        ORDER BY p.name ASC
    """)
    fun getAllWithLogCount(): Flow<List<PersonWithLogCount>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: PersonEntity)

    @Update
    suspend fun update(person: PersonEntity)

    @Delete
    suspend fun delete(person: PersonEntity)

    @Query("DELETE FROM persons")
    suspend fun deleteAll()

    @Query("SELECT * FROM persons")
    suspend fun getAllSync(): List<PersonEntity>
}

data class PersonWithLogCount(
    val id: String,
    val name: String,
    val notes: String?,
    val created_at: Long,
    val updated_at: Long,
    val log_count: Int
)
