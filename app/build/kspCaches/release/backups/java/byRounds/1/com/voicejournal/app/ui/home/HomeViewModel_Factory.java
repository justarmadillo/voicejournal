package com.voicejournal.app.ui.home;

import com.voicejournal.app.audio.AudioRecorder;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<VoiceLogRepository> voiceLogRepositoryProvider;

  private final Provider<AudioRecorder> audioRecorderProvider;

  public HomeViewModel_Factory(Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<AudioRecorder> audioRecorderProvider) {
    this.voiceLogRepositoryProvider = voiceLogRepositoryProvider;
    this.audioRecorderProvider = audioRecorderProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(voiceLogRepositoryProvider.get(), audioRecorderProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<AudioRecorder> audioRecorderProvider) {
    return new HomeViewModel_Factory(voiceLogRepositoryProvider, audioRecorderProvider);
  }

  public static HomeViewModel newInstance(VoiceLogRepository voiceLogRepository,
      AudioRecorder audioRecorder) {
    return new HomeViewModel(voiceLogRepository, audioRecorder);
  }
}
