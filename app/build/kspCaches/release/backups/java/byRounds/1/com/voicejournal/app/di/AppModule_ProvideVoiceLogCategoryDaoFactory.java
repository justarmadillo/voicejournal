package com.voicejournal.app.di;

import com.voicejournal.app.data.local.db.VoiceJournalDatabase;
import com.voicejournal.app.data.local.db.dao.VoiceLogCategoryDao;
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
public final class AppModule_ProvideVoiceLogCategoryDaoFactory implements Factory<VoiceLogCategoryDao> {
  private final Provider<VoiceJournalDatabase> databaseProvider;

  public AppModule_ProvideVoiceLogCategoryDaoFactory(
      Provider<VoiceJournalDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VoiceLogCategoryDao get() {
    return provideVoiceLogCategoryDao(databaseProvider.get());
  }

  public static AppModule_ProvideVoiceLogCategoryDaoFactory create(
      Provider<VoiceJournalDatabase> databaseProvider) {
    return new AppModule_ProvideVoiceLogCategoryDaoFactory(databaseProvider);
  }

  public static VoiceLogCategoryDao provideVoiceLogCategoryDao(VoiceJournalDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVoiceLogCategoryDao(database));
  }
}
