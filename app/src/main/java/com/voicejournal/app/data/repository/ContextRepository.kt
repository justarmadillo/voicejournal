package com.voicejournal.app.data.repository

import com.voicejournal.app.data.local.db.dao.ContextDao
import com.voicejournal.app.data.local.db.entity.ContextEntity
import com.voicejournal.app.domain.model.VoiceContext
import com.voicejournal.app.util.DateTimeUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextRepository @Inject constructor(
    private val contextDao: ContextDao
) {

    fun getAll(): Flow<List<VoiceContext>> = contextDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    fun getById(id: String): Flow<VoiceContext?> = contextDao.getById(id).map { it?.toDomain() }

    suspend fun create(name: String, notes: String? = null): VoiceContext {
        val now = DateTimeUtil.now()
        val entity = ContextEntity(
            id = UUID.randomUUID().toString(),
            name = name,
            notes = notes,
            createdAt = now,
            updatedAt = now
        )
        contextDao.insert(entity)
        return entity.toDomain()
    }

    suspend fun update(context: VoiceContext) {
        contextDao.update(
            ContextEntity(
                id = context.id,
                name = context.name,
                notes = context.notes,
                createdAt = context.createdAt,
                updatedAt = DateTimeUtil.now()
            )
        )
    }

    suspend fun delete(context: VoiceContext) {
        contextDao.delete(
            ContextEntity(
                id = context.id,
                name = context.name,
                notes = context.notes,
                createdAt = context.createdAt,
                updatedAt = context.updatedAt
            )
        )
    }

    private fun ContextEntity.toDomain() = VoiceContext(
        id = id,
        name = name,
        notes = notes,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
