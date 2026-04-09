package com.voicejournal.app.di;

import com.voicejournal.app.data.local.db.VoiceJournalDatabase;
import com.voicejournal.app.data.local.db.dao.PersonDao;
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
public final class AppModule_ProvidePersonDaoFactory implements Factory<PersonDao> {
  private final Provider<VoiceJournalDatabase> databaseProvider;

  public AppModule_ProvidePersonDaoFactory(Provider<VoiceJournalDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PersonDao get() {
    return providePersonDao(databaseProvider.get());
  }

  public static AppModule_ProvidePersonDaoFactory create(
      Provider<VoiceJournalDatabase> databaseProvider) {
    return new AppModule_ProvidePersonDaoFactory(databaseProvider);
  }

  public static PersonDao providePersonDao(VoiceJournalDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePersonDao(database));
  }
}
