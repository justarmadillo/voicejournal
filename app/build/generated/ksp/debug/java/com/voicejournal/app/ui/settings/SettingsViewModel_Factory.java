package com.voicejournal.app.ui.settings;

import android.content.Context;
import com.voicejournal.app.data.local.AutoBackupPreferences;
import com.voicejournal.app.data.repository.BackupRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
  private final Provider<Context> appContextProvider;

  private final Provider<BackupRepository> backupRepositoryProvider;

  private final Provider<AutoBackupPreferences> autoBackupPreferencesProvider;

  public SettingsViewModel_Factory(Provider<Context> appContextProvider,
      Provider<BackupRepository> backupRepositoryProvider,
      Provider<AutoBackupPreferences> autoBackupPreferencesProvider) {
    this.appContextProvider = appContextProvider;
    this.backupRepositoryProvider = backupRepositoryProvider;
    this.autoBackupPreferencesProvider = autoBackupPreferencesProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(appContextProvider.get(), backupRepositoryProvider.get(), autoBackupPreferencesProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<Context> appContextProvider,
      Provider<BackupRepository> backupRepositoryProvider,
      Provider<AutoBackupPreferences> autoBackupPreferencesProvider) {
    return new SettingsViewModel_Factory(appContextProvider, backupRepositoryProvider, autoBackupPreferencesProvider);
  }

  public static SettingsViewModel newInstance(Context appContext, BackupRepository backupRepository,
      AutoBackupPreferences autoBackupPreferences) {
    return new SettingsViewModel(appContext, backupRepository, autoBackupPreferences);
  }
}
