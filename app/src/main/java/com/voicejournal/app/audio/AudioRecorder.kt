package com.voicejournal.app.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.voicejournal.app.data.local.audio.AudioFileManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRecorder @Inject constructor(
    @ApplicationContext private val context: Context,
    private val audioFileManager: AudioFileManager
) {

    private var recorder: MediaRecorder? = null
    private var currentFileName: String? = null
    private var startTimeMs: Long = 0L

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    fun startRecording(): String {
        val fileName = audioFileManager.generateFileName()
        val file = audioFileManager.getFilePath(fileName)

        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(128000)
            setAudioSamplingRate(44100)
            setOutputFile(file.absolutePath)
            prepare()
            start()
        }

        currentFileName = fileName
        startTimeMs = System.currentTimeMillis()
        _isRecording.value = true
        return fileName
    }

    fun stopRecording(): Pair<String, Long> {
        val fileName = currentFileName ?: throw IllegalStateException("No active recording")
        val durationMs = System.currentTimeMillis() - startTimeMs

        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        _isRecording.value = false

        return Pair(fileName, durationMs)
    }

    fun cancelRecording() {
        val fileName = currentFileName
        recorder?.apply {
            try {
                stop()
            } catch (_: RuntimeException) {
                // stop() may throw if recording hasn't started properly
            }
            release()
        }
        recorder = null
        _isRecording.value = false
        fileName?.let { audioFileManager.deleteFile(it) }
        currentFileName = null
    }
}
