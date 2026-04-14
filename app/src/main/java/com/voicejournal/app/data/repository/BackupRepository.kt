package com.voicejournal.app.data.repository

import android.content.Context
import android.net.Uri
import com.voicejournal.app.data.local.audio.AudioFileManager
import com.voicejournal.app.data.local.db.dao.CategoryDao
import com.voicejournal.app.data.local.db.dao.ContextDao
import com.voicejournal.app.data.local.db.dao.PersonDao
import com.voicejournal.app.data.local.db.dao.VoiceLogCategoryDao
import com.voicejournal.app.data.local.db.dao.VoiceLogDao
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao
import com.voicejournal.app.data.local.db.entity.CategoryEntity
import com.voicejournal.app.data.local.db.entity.ContextEntity
import com.voicejournal.app.data.local.db.entity.PersonEntity
import com.voicejournal.app.data.local.db.entity.VoiceLogCategoryCrossRef
import com.voicejournal.app.data.local.db.entity.VoiceLogEntity
import com.voicejournal.app.data.local.db.entity.VoiceNoteEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.BufferedOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.inject.Inject
import javax.inject.Singleton

private const val SCHEMA_VERSION = 3
private const val DATA_FILE = "data.json"
private const val AUDIO_PREFIX = "audio/"

@Serializable
data class BackupData(
    val schemaVersion: Int = SCHEMA_VERSION,
    val exportedAt: Long = System.currentTimeMillis(),
    val persons: List<PersonBackup> = emptyList(),
    val categories: List<CategoryBackup> = emptyList(),
    val contexts: List<ContextBackup> = emptyList(),
    val voiceLogs: List<VoiceLogBackup> = emptyList(),
    val voiceLogCategories: List<VoiceLogCategoryBackup> = emptyList(),
    val voiceNotes: List<VoiceNoteBackup> = emptyList()
)

@Serializable
data class PersonBackup(val id: String, val name: String, val notes: String? = null, val createdAt: Long, val updatedAt: Long)
@Serializable
data class CategoryBackup(val id: String, val name: String, val colorHex: String? = null, val createdAt: Long, val updatedAt: Long)
@Serializable
data class ContextBackup(val id: String, val name: String, val notes: String? = null, val createdAt: Long, val updatedAt: Long)
@Serializable
data class VoiceLogBackup(val id: String, val personId: String? = null, val contextId: String? = null, val audioFileName: String, val durationMs: Long, val title: String? = null, val notes: String? = null, val isDraft: Boolean = false, val createdAt: Long, val updatedAt: Long)
@Serializable
data class VoiceLogCategoryBackup(val voiceLogId: String, val categoryId: String)
@Serializable
data class VoiceNoteBackup(val id: String, val voiceLogId: String, val audioFileName: String? = null, val durationMs: Long = 0, val textNote: String? = null, val createdAt: Long)

@Singleton
class BackupRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val personDao: PersonDao,
    private val categoryDao: CategoryDao,
    private val contextDao: ContextDao,
    private val voiceLogDao: VoiceLogDao,
    private val voiceLogCategoryDao: VoiceLogCategoryDao,
    private val voiceNoteDao: VoiceNoteDao,
    private val audioFileManager: AudioFileManager
) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        isLenient = true
    }

    suspend fun exportToZip(outputUri: Uri) = withContext(Dispatchers.IO) {
        val persons = personDao.getAllSync()
        val categories = categoryDao.getAllSync()
        val contexts = contextDao.getAllSync()
        val voiceLogs = voiceLogDao.getAllSync()
        val crossRefs = voiceLogCategoryDao.getAllSync()
        val voiceNotes = voiceNoteDao.getAllSync()

        val backupData = BackupData(
            persons = persons.map { PersonBackup(it.id, it.name, it.notes, it.createdAt, it.updatedAt) },
            categories = categories.map { CategoryBackup(it.id, it.name, it.colorHex, it.createdAt, it.updatedAt) },
            contexts = contexts.map { ContextBackup(it.id, it.name, it.notes, it.createdAt, it.updatedAt) },
            voiceLogs = voiceLogs.map { VoiceLogBackup(it.id, it.personId, it.contextId, it.audioFileName, it.durationMs, it.title, it.notes, it.isDraft, it.createdAt, it.updatedAt) },
            voiceLogCategories = crossRefs.map { VoiceLogCategoryBackup(it.voiceLogId, it.categoryId) },
            voiceNotes = voiceNotes.map { VoiceNoteBackup(it.id, it.voiceLogId, it.audioFileName, it.durationMs, it.textNote, it.createdAt) }
        )

        val outputStream = context.contentResolver.openOutputStream(outputUri)
            ?: throw IllegalStateException("Could not open output file")

        outputStream.use { rawOut ->
            ZipOutputStream(BufferedOutputStream(rawOut)).use { zip ->
                zip.putNextEntry(ZipEntry(DATA_FILE))
                zip.write(json.encodeToString(BackupData.serializer(), backupData).toByteArray(Charsets.UTF_8))
                zip.closeEntry()

                audioFileManager.getAllFiles().forEach { file ->
                    try {
                        zip.putNextEntry(ZipEntry("$AUDIO_PREFIX${file.name}"))
                        file.inputStream().use { it.copyTo(zip) }
                        zip.closeEntry()
                    } catch (_: Exception) { }
                }
            }
        }
    }

    suspend fun importFromZip(inputUri: Uri) = withContext(Dispatchers.IO) {
        val inputStream = context.contentResolver.openInputStream(inputUri)
            ?: throw IllegalStateException("Could not open input file")

        inputStream.use { rawIn ->
            ZipInputStream(rawIn).use { zip ->
                var backupData: BackupData? = null

                var entry = zip.nextEntry
                while (entry != null) {
                    try {
                        when {
                            entry.name == DATA_FILE -> {
                                val jsonStr = zip.bufferedReader(Charsets.UTF_8).readText()
                                backupData = json.decodeFromString(BackupData.serializer(), jsonStr)
                            }
                            entry.name.startsWith(AUDIO_PREFIX) && !entry.isDirectory -> {
                                val fileName = entry.name.removePrefix(AUDIO_PREFIX)
                                if (fileName.isNotBlank()) {
                                    audioFileManager.importFile(zip, fileName)
                                }
                            }
                        }
                    } catch (_: Exception) { }
                    zip.closeEntry()
                    entry = zip.nextEntry
                }

                val data = backupData ?: throw IllegalStateException("No data.json found in backup file")

                // Clear existing data (order matters due to foreign keys)
                voiceNoteDao.deleteAll()
                voiceLogCategoryDao.deleteAll()
                voiceLogDao.deleteAll()
                categoryDao.deleteAll()
                contextDao.deleteAll()
                personDao.deleteAll()

                // Restore data
                data.persons.forEach {
                    personDao.insert(PersonEntity(it.id, it.name, it.notes, it.createdAt, it.updatedAt))
                }
                data.categories.forEach {
                    categoryDao.insert(CategoryEntity(it.id, it.name, it.colorHex, it.createdAt, it.updatedAt))
                }
                data.contexts.forEach {
                    contextDao.insert(ContextEntity(it.id, it.name, it.notes, it.createdAt, it.updatedAt))
                }
                data.voiceLogs.forEach {
                    voiceLogDao.insert(VoiceLogEntity(it.id, it.personId, it.contextId, it.audioFileName, it.durationMs, it.title, it.notes, it.isDraft, it.createdAt, it.updatedAt))
                }
                data.voiceLogCategories.forEach {
                    try { voiceLogCategoryDao.insert(VoiceLogCategoryCrossRef(it.voiceLogId, it.categoryId)) }
                    catch (_: Exception) { }
                }
                data.voiceNotes.forEach {
                    try { voiceNoteDao.insert(VoiceNoteEntity(it.id, it.voiceLogId, it.audioFileName, it.durationMs, it.textNote, it.createdAt)) }
                    catch (_: Exception) { }
                }
            }
        }
    }
}
