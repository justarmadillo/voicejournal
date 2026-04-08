package com.voicejournal.app.ui.people;

import androidx.lifecycle.SavedStateHandle;
import com.voicejournal.app.audio.AudioPlayer;
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
public final class PersonDetailViewModel_Factory implements Factory<PersonDetailViewModel> {
  private final Provider<PersonRepository> personRepositoryProvider;

  private final Provider<VoiceLogRepository> voiceLogRepositoryProvider;

  private final Provider<AudioPlayer> audioPlayerProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public PersonDetailViewModel_Factory(Provider<PersonRepository> personRepositoryProvider,
      Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<AudioPlayer> audioPlayerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.personRepositoryProvider = personRepositoryProvider;
    this.voiceLogRepositoryProvider = voiceLogRepositoryProvider;
    this.audioPlayerProvider = audioPlayerProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public PersonDetailViewModel get() {
    return newInstance(personRepositoryProvider.get(), voiceLogRepositoryProvider.get(), audioPlayerProvider.get(), savedStateHandleProvider.get());
  }

  public static PersonDetailViewModel_Factory create(
      Provider<PersonRepository> personRepositoryProvider,
      Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<AudioPlayer> audioPlayerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new PersonDetailViewModel_Factory(personRepositoryProvider, voiceLogRepositoryProvider, audioPlayerProvider, savedStateHandleProvider);
  }

  public static PersonDetailViewModel newInstance(PersonRepository personRepository,
      VoiceLogRepository voiceLogRepository, AudioPlayer audioPlayer,
      SavedStateHandle savedStateHandle) {
    return new PersonDetailViewModel(personRepository, voiceLogRepository, audioPlayer, savedStateHandle);
  }
}
