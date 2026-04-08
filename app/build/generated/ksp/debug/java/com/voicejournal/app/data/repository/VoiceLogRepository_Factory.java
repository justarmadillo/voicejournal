package com.voicejournal.app.data.repository;

import com.voicejournal.app.data.local.audio.AudioFileManager;
import com.voicejournal.app.data.local.db.dao.PersonDao;
import com.voicejournal.app.data.local.db.dao.VoiceLogCategoryDao;
import com.voicejournal.app.data.local.db.dao.VoiceLogDao;
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class VoiceLogRepository_Factory implements Factory<VoiceLogRepository> {
  private final Provider<VoiceLogDao> voiceLogDaoProvider;

  private final Provider<VoiceLogCategoryDao> voiceLogCategoryDaoProvider;

  private final Provider<VoiceNoteDao> voiceNoteDaoProvider;

  private final Provider<PersonDao> personDaoProvider;

  private final Provider<AudioFileManager> audioFileManagerProvider;

  public VoiceLogRepository_Factory(Provider<VoiceLogDao> voiceLogDaoProvider,
      Provider<VoiceLogCategoryDao> voiceLogCategoryDaoProvider,
      Provider<VoiceNoteDao> voiceNoteDaoProvider, Provider<PersonDao> personDaoProvider,
      Provider<AudioFileManager> audioFileManagerProvider) {
    this.voiceLogDaoProvider = voiceLogDaoProvider;
    this.voiceLogCategoryDaoProvider = voiceLogCategoryDaoProvider;
    this.voiceNoteDaoProvider = voiceNoteDaoProvider;
    this.personDaoProvider = personDaoProvider;
    this.audioFileManagerProvider = audioFileManagerProvider;
  }

  @Override
  public VoiceLogRepository get() {
    return newInstance(voiceLogDaoProvider.get(), voiceLogCategoryDaoProvider.get(), voiceNoteDaoProvider.get(), personDaoProvider.get(), audioFileManagerProvider.get());
  }

  public static VoiceLogRepository_Factory create(Provider<VoiceLogDao> voiceLogDaoProvider,
      Provider<VoiceLogCategoryDao> voiceLogCategoryDaoProvider,
      Provider<VoiceNoteDao> voiceNoteDaoProvider, Provider<PersonDao> personDaoProvider,
      Provider<AudioFileManager> audioFileManagerProvider) {
    return new VoiceLogRepository_Factory(voiceLogDaoProvider, voiceLogCategoryDaoProvider, voiceNoteDaoProvider, personDaoProvider, audioFileManagerProvider);
  }

  public static VoiceLogRepository newInstance(VoiceLogDao voiceLogDao,
      VoiceLogCategoryDao voiceLogCategoryDao, VoiceNoteDao voiceNoteDao, PersonDao personDao,
      AudioFileManager audioFileManager) {
    return new VoiceLogRepository(voiceLogDao, voiceLogCategoryDao, voiceNoteDao, personDao, audioFileManager);
  }
}
