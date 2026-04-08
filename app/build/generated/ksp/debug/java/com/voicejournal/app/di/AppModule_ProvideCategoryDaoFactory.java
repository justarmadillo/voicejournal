package com.voicejournal.app.di;

import com.voicejournal.app.data.local.db.VoiceJournalDatabase;
import com.voicejournal.app.data.local.db.dao.CategoryDao;
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
public final class AppModule_ProvideCategoryDaoFactory implements Factory<CategoryDao> {
  private final Provider<VoiceJournalDatabase> databaseProvider;

  public AppModule_ProvideCategoryDaoFactory(Provider<VoiceJournalDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CategoryDao get() {
    return provideCategoryDao(databaseProvider.get());
  }

  public static AppModule_ProvideCategoryDaoFactory create(
      Provider<VoiceJournalDatabase> databaseProvider) {
    return new AppModule_ProvideCategoryDaoFactory(databaseProvider);
  }

  public static CategoryDao provideCategoryDao(VoiceJournalDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCategoryDao(database));
  }
}
