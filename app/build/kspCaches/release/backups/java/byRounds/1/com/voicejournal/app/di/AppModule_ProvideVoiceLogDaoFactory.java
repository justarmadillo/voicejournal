package com.voicejournal.app.di;

import com.voicejournal.app.data.local.db.VoiceJournalDatabase;
import com.voicejournal.app.data.local.db.dao.VoiceLogDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideVoiceLogDaoFactory implements Factory<VoiceLogDao> {
  private final Provider<VoiceJournalDatabase> databaseProvider;

  public AppModule_ProvideVoiceLogDaoFactory(Provider<VoiceJournalDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VoiceLogDao get() {
    return provideVoiceLogDao(databaseProvider.get());
  }

  public static AppModule_ProvideVoiceLogDaoFactory create(
      Provider<VoiceJournalDatabase> databaseProvider) {
    return new AppModule_ProvideVoiceLogDaoFactory(databaseProvider);
  }

  public static VoiceLogDao provideVoiceLogDao(VoiceJournalDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVoiceLogDao(database));
  }
}
