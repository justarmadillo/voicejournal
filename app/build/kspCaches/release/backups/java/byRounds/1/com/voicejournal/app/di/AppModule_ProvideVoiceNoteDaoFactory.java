package com.voicejournal.app.di;

import com.voicejournal.app.data.local.db.VoiceJournalDatabase;
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao;
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
public final class AppModule_ProvideVoiceNoteDaoFactory implements Factory<VoiceNoteDao> {
  private final Provider<VoiceJournalDatabase> databaseProvider;

  public AppModule_ProvideVoiceNoteDaoFactory(Provider<VoiceJournalDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VoiceNoteDao get() {
    return provideVoiceNoteDao(databaseProvider.get());
  }

  public static AppModule_ProvideVoiceNoteDaoFactory create(
      Provider<VoiceJournalDatabase> databaseProvider) {
    return new AppModule_ProvideVoiceNoteDaoFactory(databaseProvider);
  }

  public static VoiceNoteDao provideVoiceNoteDao(VoiceJournalDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVoiceNoteDao(database));
  }
}
