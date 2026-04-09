package com.voicejournal.app.data.repository;

import android.content.Context;
import com.voicejournal.app.data.local.audio.AudioFileManager;
import com.voicejournal.app.data.local.db.dao.CategoryDao;
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
public final class BackupRepository_Factory implements Factory<BackupRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<PersonDao> personDaoProvider;

  private final Provider<CategoryDao> categoryDaoProvider;

  private final Provider<VoiceLogDao> voiceLogDaoProvider;

  private final Provider<VoiceLogCategoryDao> voiceLogCategoryDaoProvider;

  private final Provider<VoiceNoteDao> voiceNoteDaoProvider;

  private final Provider<AudioFileManager> audioFileManagerProvider;

  public BackupRepository_Factory(Provider<Context> contextProvider,
      Provider<PersonDao> personDaoProvider, Provider<CategoryDao> categoryDaoProvider,
      Provider<VoiceLogDao> voiceLogDaoProvider,
      Provider<VoiceLogCategoryDao> voiceLogCategoryDaoProvider,
      Provider<VoiceNoteDao> voiceNoteDaoProvider,
      Provider<AudioFileManager> audioFileManagerProvider) {
    this.contextProvider = contextProvider;
    this.personDaoProvider = personDaoProvider;
    this.categoryDaoProvider = categoryDaoProvider;
    this.voiceLogDaoProvider = voiceLogDaoProvider;
    this.voiceLogCategoryDaoProvider = voiceLogCategoryDaoProvider;
    this.voiceNoteDaoProvider = voiceNoteDaoProvider;
    this.audioFileManagerProvider = audioFileManagerProvider;
  }

  @Override
  public BackupRepository get() {
    return newInstance(contextProvider.get(), personDaoProvider.get(), categoryDaoProvider.get(), voiceLogDaoProvider.get(), voiceLogCategoryDaoProvider.get(), voiceNoteDaoProvider.get(), audioFileManagerProvider.get());
  }

  public static BackupRepository_Factory create(Provider<Context> contextProvider,
      Provider<PersonDao> personDaoProvider, Provider<CategoryDao> categoryDaoProvider,
      Provider<VoiceLogDao> voiceLogDaoProvider,
      Provider<VoiceLogCategoryDao> voiceLogCategoryDaoProvider,
      Provider<VoiceNoteDao> voiceNoteDaoProvider,
      Provider<AudioFileManager> audioFileManagerProvider) {
    return new BackupRepository_Factory(contextProvider, personDaoProvider, categoryDaoProvider, voiceLogDaoProvider, voiceLogCategoryDaoProvider, voiceNoteDaoProvider, audioFileManagerProvider);
  }

  public static BackupRepository newInstance(Context context, PersonDao personDao,
      CategoryDao categoryDao, VoiceLogDao voiceLogDao, VoiceLogCategoryDao voiceLogCategoryDao,
      VoiceNoteDao voiceNoteDao, AudioFileManager audioFileManager) {
    return new BackupRepository(context, personDao, categoryDao, voiceLogDao, voiceLogCategoryDao, voiceNoteDao, audioFileManager);
  }
}
