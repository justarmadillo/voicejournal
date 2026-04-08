package com.voicejournal.app.audio

import android.media.MediaPlayer
import com.voicejournal.app.data.local.audio.AudioFileManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    fun play(fileName: String) {
        // Stop current playback if different file
        if (_playbackState.value.currentFileName != fileName) {
            stop()
        }

        if (player == null) {
            val file = audioFileManager.getFilePath(fileName)
            player = MediaPlayer().apply {
                setDataSource(file.absolutePath)
                prepare()
                setOnCompletionListener {
                    _playbackState.value = _playbackState.value.copy(
                        isPlaying = false,
                        currentPositionMs = 0
                    )
                    positionJob?.cancel()
                }
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
    }

    fun pause() {
        player?.pause()
        positionJob?.cancel()
        _playbackState.value = _playbackState.value.copy(isPlaying = false)
    }

    fun stop() {
        positionJob?.cancel()
        player?.release()
        player = null
        _playbackState.value = PlaybackState()
    }

    fun seekTo(positionMs: Int) {
        player?.seekTo(positionMs)
        _playbackState.value = _playbackState.value.copy(currentPositionMs = positionMs)
    }

    private fun startPositionTracking() {
        positionJob?.cancel()
        positionJob = scope.launch {
            while (true) {
                player?.let {
                    if (it.isPlaying) {
                        _playbackState.value = _playbackState.value.copy(
                            currentPositionMs = it.currentPosition
                        )
                    }
                }
                delay(200)
            }
        }
    }
}
