package com.voicejournal.app.data.local.audio

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioFileManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val audioDir: File
        get() = File(context.filesDir, "audio").also { it.mkdirs() }

    fun getFilePath(fileName: String): File = File(audioDir, fileName)

    fun deleteFile(fileName: String): Boolean = getFilePath(fileName).delete()

    fun getAllFiles(): List<File> = audioDir.listFiles()?.toList() ?: emptyList()

    fun importFile(source: InputStream, fileName: String) {
        getFilePath(fileName).outputStream().use { out ->
            source.copyTo(out)
        }
    }

    fun generateFileName(): String = "rec_${System.currentTimeMillis()}.m4a"
}
