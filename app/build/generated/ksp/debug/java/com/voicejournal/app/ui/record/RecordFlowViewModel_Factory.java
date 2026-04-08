package com.voicejournal.app.ui.record;

import androidx.lifecycle.SavedStateHandle;
import com.voicejournal.app.data.local.audio.AudioFileManager;
import com.voicejournal.app.data.repository.CategoryRepository;
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
public final class RecordFlowViewModel_Factory implements Factory<RecordFlowViewModel> {
  private final Provider<PersonRepository> personRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<VoiceLogRepository> voiceLogRepositoryProvider;

  private final Provider<AudioFileManager> audioFileManagerProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public RecordFlowViewModel_Factory(Provider<PersonRepository> personRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<AudioFileManager> audioFileManagerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.personRepositoryProvider = personRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.voiceLogRepositoryProvider = voiceLogRepositoryProvider;
    this.audioFileManagerProvider = audioFileManagerProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public RecordFlowViewModel get() {
    return newInstance(personRepositoryProvider.get(), categoryRepositoryProvider.get(), voiceLogRepositoryProvider.get(), audioFileManagerProvider.get(), savedStateHandleProvider.get());
  }

  public static RecordFlowViewModel_Factory create(
      Provider<PersonRepository> personRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<VoiceLogRepository> voiceLogRepositoryProvider,
      Provider<AudioFileManager> audioFileManagerProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new RecordFlowViewModel_Factory(personRepositoryProvider, categoryRepositoryProvider, voiceLogRepositoryProvider, audioFileManagerProvider, savedStateHandleProvider);
  }

  public static RecordFlowViewModel newInstance(PersonRepository personRepository,
      CategoryRepository categoryRepository, VoiceLogRepository voiceLogRepository,
      AudioFileManager audioFileManager, SavedStateHandle savedStateHandle) {
    return new RecordFlowViewModel(personRepository, categoryRepository, voiceLogRepository, audioFileManager, savedStateHandle);
  }
}
