package com.voicejournal.app.ui.logdetail;

import androidx.lifecycle.SavedStateHandle;
import com.voicejournal.app.audio.AudioPlayer;
import com.voicejournal.app.audio.AudioRecorder;
import com.voicejournal.app.data.local.audio.AudioFileManager;
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao;
import com.voicejournal.app.data.repository.CategoryRepository;
import com.voicejournal.app.data.repository.VoiceLogRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class LogDetailViewModel_Factory implements Factory<LogDetailViewModel> {
  private final Provider<VoiceLogRepository> voiceLogRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<VoiceNoteDao> voiceNoteDaoProvider;

  private final Provider<AudioFileManager> audioFileManagerProvider;

  private final Provider<AudioPlayer> audioPlayerProvider;

  private final Provider<AudioRecorder> audioRecorderProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public LogDetailViewModel_Factory(Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<VoiceNoteDao> voiceNoteDaoProvider,
      Provider<AudioFileManager> audioFileManagerProvider,
      Provider<AudioPlayer> audioPlayerProvider, Provider<AudioRecorder> audioRecorderProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.voiceLogRepositoryProvider = voiceLogRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.voiceNoteDaoProvider = voiceNoteDaoProvider;
    this.audioFileManagerProvider = audioFileManagerProvider;
    this.audioPlayerProvider = audioPlayerProvider;
    this.audioRecorderProvider = audioRecorderProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public LogDetailViewModel get() {
    return newInstance(voiceLogRepositoryProvider.get(), categoryRepositoryProvider.get(), voiceNoteDaoProvider.get(), audioFileManagerProvider.get(), audioPlayerProvider.get(), audioRecorderProvider.get(), savedStateHandleProvider.get());
  }

  public static LogDetailViewModel_Factory create(
      Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<VoiceNoteDao> voiceNoteDaoProvider,
      Provider<AudioFileManager> audioFileManagerProvider,
      Provider<AudioPlayer> audioPlayerProvider, Provider<AudioRecorder> audioRecorderProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new LogDetailViewModel_Factory(voiceLogRepositoryProvider, categoryRepositoryProvider, voiceNoteDaoProvider, audioFileManagerProvider, audioPlayerProvider, audioRecorderProvider, savedStateHandleProvider);
  }

  public static LogDetailViewModel newInstance(VoiceLogRepository voiceLogRepository,
      CategoryRepository categoryRepository, VoiceNoteDao voiceNoteDao,
      AudioFileManager audioFileManager, AudioPlayer audioPlayer, AudioRecorder audioRecorder,
      SavedStateHandle savedStateHandle) {
    return new LogDetailViewModel(voiceLogRepository, categoryRepository, voiceNoteDao, audioFileManager, audioPlayer, audioRecorder, savedStateHandle);
  }
}
