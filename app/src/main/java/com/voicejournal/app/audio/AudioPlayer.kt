package com.voicejournal.app.audio

import android.media.MediaPlayer
import com.voicejournal.app.data.local.audio.AudioFileManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

data class PlaybackState(
    val isPlaying: Boolean = false,
    val currentPositionMs: Int = 0,
    val durationMs: Int = 0,
    val currentFileName: String? = null
)

@Singleton
class AudioPlayer @Inject constructor(
    private val audioFileManager: AudioFileManager
) {

    private var player: MediaPlayer? = null
    private var positionJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    fun play(fileName: String) {
        // Stop current playback if different file
        if (_playbackState.value.currentFileName != fileName) {
            stop()
        }

        try {
            if (player == null) {
                val file = audioFileManager.getFilePath(fileName)
                if (!file.exists()) {
                    _playbackState.value = PlaybackState()
                    return
                }
                player = MediaPlayer().apply {
                    setDataSource(file.absolutePath)
                    setOnErrorListener { _, _, _ ->
                        stop()
                        true
                    }
                    setOnCompletionListener {
                        _playbackState.value = _playbackState.value.copy(
                            isPlaying = false,
                            currentPositionMs = 0
                        )
                        positionJob?.cancel()
                    }
                    prepare()
                }
            }

            player?.start()
            _playbackState.value = PlaybackState(
                isPlaying = true,
                currentPositionMs = player?.currentPosition ?: 0,
                durationMs = player?.duration ?: 0,
                currentFileName = fileName
            )
            startPositionTracking()
        } catch (_: Exception) {
            releasePlayer()
            _playbackState.value = PlaybackState()
        }
    }

    fun pause() {
        try {
            player?.pause()
        } catch (_: Exception) { }
        positionJob?.cancel()
        _playbackState.value = _playbackState.value.copy(isPlaying = false)
    }

    fun stop() {
        positionJob?.cancel()
        releasePlayer()
        _playbackState.value = PlaybackState()
    }

    fun seekTo(positionMs: Int) {
        try {
            player?.seekTo(positionMs)
            _playbackState.value = _playbackState.value.copy(currentPositionMs = positionMs)
        } catch (_: Exception) { }
    }

    private fun releasePlayer() {
        try {
            player?.release()
        } catch (_: Exception) { }
        player = null
    }

    private fun startPositionTracking() {
        positionJob?.cancel()
        positionJob = scope.launch {
            while (true) {
                try {
                    player?.let {
                        if (it.isPlaying) {
                            _playbackState.value = _playbackState.value.copy(
                                currentPositionMs = it.currentPosition
                            )
                        }
                    }
                } catch (_: Exception) { }
                delay(200)
            }
        }
    }
}
