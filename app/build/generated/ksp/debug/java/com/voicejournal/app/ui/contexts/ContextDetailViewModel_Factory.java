package com.voicejournal.app.ui.contexts;

import androidx.lifecycle.SavedStateHandle;
import com.voicejournal.app.audio.AudioPlayer;
import com.voicejournal.app.data.repository.ContextRepository;
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
public final class ContextDetailViewModel_Factory implements Factory<ContextDetailViewModel> {
  private final Provider<ContextRepository> contextRepositoryProvider;

  private final Provider<VoiceLogRepository> voiceLogRepositoryProvider;

  private final Provider<AudioPlayer> audioPlayerProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public ContextDetailViewModel_Factory(Provider<ContextRepository> contextRepositoryProvider,
      Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<AudioPlayer> audioPlayerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.contextRepositoryProvider = contextRepositoryProvider;
    this.voiceLogRepositoryProvider = voiceLogRepositoryProvider;
    this.audioPlayerProvider = audioPlayerProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public ContextDetailViewModel get() {
    return newInstance(contextRepositoryProvider.get(), voiceLogRepositoryProvider.get(), audioPlayerProvider.get(), savedStateHandleProvider.get());
  }

  public static ContextDetailViewModel_Factory create(
      Provider<ContextRepository> contextRepositoryProvider,
      Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<AudioPlayer> audioPlayerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new ContextDetailViewModel_Factory(contextRepositoryProvider, voiceLogRepositoryProvider, audioPlayerProvider, savedStateHandleProvider);
  }

  public static ContextDetailViewModel newInstance(ContextRepository contextRepository,
      VoiceLogRepository voiceLogRepository, AudioPlayer audioPlayer,
      SavedStateHandle savedStateHandle) {
    return new ContextDetailViewModel(contextRepository, voiceLogRepository, audioPlayer, savedStateHandle);
  }
}
