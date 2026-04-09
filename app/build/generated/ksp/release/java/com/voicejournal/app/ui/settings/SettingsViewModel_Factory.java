package com.voicejournal.app.ui.settings;

import com.voicejournal.app.data.repository.BackupRepository;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<BackupRepository> backupRepositoryProvider;

  public SettingsViewModel_Factory(Provider<BackupRepository> backupRepositoryProvider) {
    this.backupRepositoryProvider = backupRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(backupRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<BackupRepository> backupRepositoryProvider) {
    return new SettingsViewModel_Factory(backupRepositoryProvider);
  }

  public static SettingsViewModel newInstance(BackupRepository backupRepository) {
    return new SettingsViewModel(backupRepository);
  }
}
