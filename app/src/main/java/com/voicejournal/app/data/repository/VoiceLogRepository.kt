package com.voicejournal.app.data.repository

import com.voicejournal.app.data.local.audio.AudioFileManager
import com.voicejournal.app.data.local.db.dao.ContextDao
import com.voicejournal.app.data.local.db.dao.PersonDao
import com.voicejournal.app.data.local.db.dao.VoiceLogCategoryDao
import com.voicejournal.app.data.local.db.dao.VoiceLogDao
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao
import com.voicejournal.app.data.local.db.entity.VoiceLogCategoryCrossRef
import com.voicejournal.app.data.local.db.entity.VoiceLogEntity
import com.voicejournal.app.data.local.db.relation.CategoryCount
import com.voicejournal.app.data.local.db.relation.VoiceLogWithCategories
import com.voicejournal.app.domain.model.Category
import com.voicejournal.app.domain.model.VoiceLog
import com.voicejournal.app.util.DateTimeUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoiceLogRepository @Inject constructor(
    private val voiceLogDao: VoiceLogDao,
    private val voiceLogCategoryDao: VoiceLogCategoryDao,
    private val voiceNoteDao: VoiceNoteDao,
    private val personDao: PersonDao,
    private val contextDao: ContextDao,
    private val audioFileManager: AudioFileManager
) {

    fun getRecentLogs(limit: Int = 20): Flow<List<VoiceLog>> {
        return combine(
            voiceLogDao.getRecentLogs(limit),
            personDao.getAll(),
            contextDao.getAll(),
            voiceNoteDao.getAllNoteCounts()
        ) { logs, persons, contexts, noteCounts ->
            val personMap = persons.associateBy { it.id }
            val contextMap = contexts.associateBy { it.id }
            val noteCountMap = noteCounts.associate { it.voiceLogId to it.count }
            logs.map { log ->
                val name = when {
                    log.voiceLog.isDraft -> "Draft"
                    log.voiceLog.personId != null -> personMap[log.voiceLog.personId]?.name ?: "Unknown"
                    log.voiceLog.contextId != null -> contextMap[log.voiceLog.contextId]?.name ?: "Unknown"
                    else -> "Unknown"
                }
                log.toDomain(name, noteCountMap[log.voiceLog.id] ?: 0)
            }.sortedWith(compareByDescending<VoiceLog> { it.isDraft }.thenByDescending { it.createdAt })
        }
    }

    fun getByIdWithCategories(id: String): Flow<VoiceLog?> {
        return combine(
            voiceLogDao.getByIdWithCategories(id),
            personDao.getAll(),
            contextDao.getAll(),
            voiceNoteDao.getAllNoteCounts()
        ) { log, persons, contexts, noteCounts ->
            log?.let {
                val name = when {
                    it.voiceLog.isDraft -> "Draft"
                    it.voiceLog.personId != null -> persons.find { p -> p.id == it.voiceLog.personId }?.name ?: "Unknown"
                    it.voiceLog.contextId != null -> contexts.find { c -> c.id == it.voiceLog.contextId }?.name ?: "Unknown"
                    else -> "Unknown"
                }
                val noteCount = noteCounts.find { nc -> nc.voiceLogId == it.voiceLog.id }?.count ?: 0
                it.toDomain(name, noteCount)
            }
        }
    }

    fun getByPersonId(personId: String): Flow<List<VoiceLog>> {
        return combine(
            voiceLogDao.getByPersonId(personId),
            personDao.getById(personId),
            voiceNoteDao.getAllNoteCounts()
        ) { logs, person, noteCounts ->
            val name = person?.name ?: "Unknown"
            val noteCountMap = noteCounts.associate { it.voiceLogId to it.count }
            logs.map { it.toDomain(name, noteCountMap[it.voiceLog.id] ?: 0) }
        }
    }

    fun getByContextId(contextId: String): Flow<List<VoiceLog>> {
        return combine(
            voiceLogDao.getByContextId(contextId),
            contextDao.getById(contextId),
            voiceNoteDao.getAllNoteCounts()
        ) { logs, context, noteCounts ->
            val name = context?.name ?: "Unknown"
            val noteCountMap = noteCounts.associate { it.voiceLogId to it.count }
            logs.map { it.toDomain(name, noteCountMap[it.voiceLog.id] ?: 0) }
        }
    }

    fun getCategoryStatsForPerson(personId: String): Flow<List<CategoryCount>> {
        return voiceLogDao.getCategoryStatsForPerson(personId)
    }

    fun getCategoryStatsForContext(contextId: String): Flow<List<CategoryCount>> {
        return voiceLogDao.getCategoryStatsForContext(contextId)
    }

    suspend fun saveDraft(audioFileName: String, durationMs: Long): String {
        val now = DateTimeUtil.now()
        val logId = UUID.randomUUID().toString()
        val entity = VoiceLogEntity(
            id = logId,
            audioFileName = audioFileName,
            durationMs = durationMs,
            isDraft = true,
            createdAt = now,
            updatedAt = now
        )
        voiceLogDao.insert(entity)
        return logId
    }

    suspend fun finalizeDraft(
        draftId: String,
        personId: String?,
        contextId: String?,
        categoryIds: List<String>,
        notes: String? = null
    ) {
        voiceLogDao.finalizeDraft(draftId, personId, contextId, notes, DateTimeUtil.now())
        voiceLogCategoryDao.deleteByVoiceLogId(draftId)
        if (categoryIds.isNotEmpty()) {
            val crossRefs = categoryIds.map { catId ->
                VoiceLogCategoryCrossRef(voiceLogId = draftId, categoryId = catId)
            }
            voiceLogCategoryDao.insertAll(crossRefs)
        }
    }

    suspend fun create(
        audioFileName: String,
        durationMs: Long,
        personId: String? = null,
        contextId: String? = null,
        categoryIds: List<String>,
        title: String? = null,
        notes: String? = null
    ): String {
        val now = DateTimeUtil.now()
        val logId = UUID.randomUUID().toString()

        val entity = VoiceLogEntity(
            id = logId,
            personId = personId,
            contextId = contextId,
            audioFileName = audioFileName,
            durationMs = durationMs,
            title = title,
            notes = notes,
            createdAt = now,
            updatedAt = now
        )
        voiceLogDao.insert(entity)

        if (categoryIds.isNotEmpty()) {
            val crossRefs = categoryIds.map { catId ->
                VoiceLogCategoryCrossRef(voiceLogId = logId, categoryId = catId)
            }
            voiceLogCategoryDao.insertAll(crossRefs)
        }

        return logId
    }

    suspend fun updateNotes(voiceLogId: String, notes: String) {
        voiceLogDao.updateNotes(voiceLogId, notes, DateTimeUtil.now())
    }

    suspend fun updateCategories(voiceLogId: String, categoryIds: List<String>) {
        voiceLogCategoryDao.deleteByVoiceLogId(voiceLogId)
        if (categoryIds.isNotEmpty()) {
            val crossRefs = categoryIds.map { catId ->
                VoiceLogCategoryCrossRef(voiceLogId = voiceLogId, categoryId = catId)
            }
            voiceLogCategoryDao.insertAll(crossRefs)
        }
    }

    suspend fun delete(voiceLog: VoiceLog) {
        audioFileManager.deleteFile(voiceLog.audioFileName)
        voiceLogDao.deleteById(voiceLog.id)
    }

    suspend fun deleteMultiple(voiceLogs: List<VoiceLog>) {
        voiceLogs.forEach { log ->
            audioFileManager.deleteFile(log.audioFileName)
            voiceLogDao.deleteById(log.id)
        }
    }

    private fun VoiceLogWithCategories.toDomain(subjectName: String, noteCount: Int = 0) = VoiceLog(
        id = voiceLog.id,
        personId = voiceLog.personId,
        contextId = voiceLog.contextId,
        subjectName = subjectName,
        audioFileName = voiceLog.audioFileName,
        durationMs = voiceLog.durationMs,
        title = voiceLog.title,
        notes = voiceLog.notes,
        categories = categories.map { cat ->
            Category(
                id = cat.id,
                name = cat.name,
                colorHex = cat.colorHex,
                createdAt = cat.createdAt,
                updatedAt = cat.updatedAt
            )
        },
        voiceNoteCount = noteCount,
        isDraft = voiceLog.isDraft,
        createdAt = voiceLog.createdAt,
        updatedAt = voiceLog.updatedAt
    )
}
