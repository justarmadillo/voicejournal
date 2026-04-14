package com.voicejournal.app.ui.record;

import androidx.lifecycle.SavedStateHandle;
import com.voicejournal.app.audio.AudioPlayer;
import com.voicejournal.app.data.repository.CategoryRepository;
import com.voicejournal.app.data.repository.ContextRepository;
import com.voicejournal.app.data.repository.PersonRepository;
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
public final class FinalizeDraftViewModel_Factory implements Factory<FinalizeDraftViewModel> {
  private final Provider<PersonRepository> personRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<ContextRepository> contextRepositoryProvider;

  private final Provider<VoiceLogRepository> voiceLogRepositoryProvider;

  private final Provider<AudioPlayer> audioPlayerProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public FinalizeDraftViewModel_Factory(Provider<PersonRepository> personRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<ContextRepository> contextRepositoryProvider,
      Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<AudioPlayer> audioPlayerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.personRepositoryProvider = personRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.contextRepositoryProvider = contextRepositoryProvider;
    this.voiceLogRepositoryProvider = voiceLogRepositoryProvider;
    this.audioPlayerProvider = audioPlayerProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public FinalizeDraftViewModel get() {
    return newInstance(personRepositoryProvider.get(), categoryRepositoryProvider.get(), contextRepositoryProvider.get(), voiceLogRepositoryProvider.get(), audioPlayerProvider.get(), savedStateHandleProvider.get());
  }

  public static FinalizeDraftViewModel_Factory create(
      Provider<PersonRepository> personRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<ContextRepository> contextRepositoryProvider,
      Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<AudioPlayer> audioPlayerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new FinalizeDraftViewModel_Factory(personRepositoryProvider, categoryRepositoryProvider, contextRepositoryProvider, voiceLogRepositoryProvider, audioPlayerProvider, savedStateHandleProvider);
  }

  public static FinalizeDraftViewModel newInstance(PersonRepository personRepository,
      CategoryRepository categoryRepository, ContextRepository contextRepository,
      VoiceLogRepository voiceLogRepository, AudioPlayer audioPlayer,
      SavedStateHandle savedStateHandle) {
    return new FinalizeDraftViewModel(personRepository, categoryRepository, contextRepository, voiceLogRepository, audioPlayer, savedStateHandle);
  }
}
