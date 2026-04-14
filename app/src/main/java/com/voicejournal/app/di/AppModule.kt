package com.voicejournal.app.di

import android.content.Context
import androidx.room.Room
import com.voicejournal.app.data.local.db.MIGRATION_1_2
import com.voicejournal.app.data.local.db.MIGRATION_2_3
import com.voicejournal.app.data.local.db.MIGRATION_3_4
import com.voicejournal.app.data.local.db.VoiceJournalDatabase
import com.voicejournal.app.data.local.db.dao.CategoryDao
import com.voicejournal.app.data.local.db.dao.ContextDao
import com.voicejournal.app.data.local.db.dao.PersonDao
import com.voicejournal.app.data.local.db.dao.VoiceLogCategoryDao
import com.voicejournal.app.data.local.db.dao.VoiceLogDao
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VoiceJournalDatabase {
        return Room.databaseBuilder(
            context,
            VoiceJournalDatabase::class.java,
            "voice_journal.db"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

    @Provides
    fun providePersonDao(database: VoiceJournalDatabase): PersonDao = database.personDao()

    @Provides
    fun provideCategoryDao(database: VoiceJournalDatabase): CategoryDao = database.categoryDao()

    @Provides
    fun provideContextDao(database: VoiceJournalDatabase): ContextDao = database.contextDao()

    @Provides
    fun provideVoiceLogDao(database: VoiceJournalDatabase): VoiceLogDao = database.voiceLogDao()

    @Provides
    fun provideVoiceLogCategoryDao(database: VoiceJournalDatabase): VoiceLogCategoryDao = database.voiceLogCategoryDao()

    @Provides
    fun provideVoiceNoteDao(database: VoiceJournalDatabase): VoiceNoteDao = database.voiceNoteDao()
}
