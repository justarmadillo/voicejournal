package com.voicejournal.app.data.local.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.voicejournal.app.data.local.db.dao.CategoryDao;
import com.voicejournal.app.data.local.db.dao.CategoryDao_Impl;
import com.voicejournal.app.data.local.db.dao.PersonDao;
import com.voicejournal.app.data.local.db.dao.PersonDao_Impl;
import com.voicejournal.app.data.local.db.dao.VoiceLogCategoryDao;
import com.voicejournal.app.data.local.db.dao.VoiceLogCategoryDao_Impl;
import com.voicejournal.app.data.local.db.dao.VoiceLogDao;
import com.voicejournal.app.data.local.db.dao.VoiceLogDao_Impl;
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao;
import com.voicejournal.app.data.local.db.dao.VoiceNoteDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VoiceJournalDatabase_Impl extends VoiceJournalDatabase {
  private volatile PersonDao _personDao;

  private volatile CategoryDao _categoryDao;

  private volatile VoiceLogDao _voiceLogDao;

  private volatile VoiceLogCategoryDao _voiceLogCategoryDao;

  private volatile VoiceNoteDao _voiceNoteDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `persons` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `notes` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_persons_name` ON `persons` (`name`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `color_hex` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_categories_name` ON `categories` (`name`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `voice_logs` (`id` TEXT NOT NULL, `person_id` TEXT, `audio_file_name` TEXT NOT NULL, `duration_ms` INTEGER NOT NULL, `title` TEXT, `notes` TEXT, `is_draft` INTEGER NOT NULL DEFAULT 0, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`person_id`) REFERENCES `persons`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_voice_logs_person_id` ON `voice_logs` (`person_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_voice_logs_created_at` ON `voice_logs` (`created_at`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `voice_log_categories` (`voice_log_id` TEXT NOT NULL, `category_id` TEXT NOT NULL, PRIMARY KEY(`voice_log_id`, `category_id`), FOREIGN KEY(`voice_log_id`) REFERENCES `voice_logs`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`category_id`) REFERENCES `categories`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_voice_log_categories_category_id` ON `voice_log_categories` (`category_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `voice_notes` (`id` TEXT NOT NULL, `voice_log_id` TEXT NOT NULL, `audio_file_name` TEXT, `duration_ms` INTEGER NOT NULL, `text_note` TEXT, `created_at` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`voice_log_id`) REFERENCES `voice_logs`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_voice_notes_voice_log_id` ON `voice_notes` (`voice_log_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1c8d1cc116ddef49e6fc59ac53878200')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `persons`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `voice_logs`");
        db.execSQL("DROP TABLE IF EXISTS `voice_log_categories`");
        db.execSQL("DROP TABLE IF EXISTS `voice_notes`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsPersons = new HashMap<String, TableInfo.Column>(5);
        _columnsPersons.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPersons.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPersons = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPersons = new HashSet<TableInfo.Index>(1);
        _indicesPersons.add(new TableInfo.Index("index_persons_name", false, Arrays.asList("name"), Arrays.asList("ASC")));
        final TableInfo _infoPersons = new TableInfo("persons", _columnsPersons, _foreignKeysPersons, _indicesPersons);
        final TableInfo _existingPersons = TableInfo.read(db, "persons");
        if (!_infoPersons.equals(_existingPersons)) {
          return new RoomOpenHelper.ValidationResult(false, "persons(com.voicejournal.app.data.local.db.entity.PersonEntity).\n"
                  + " Expected:\n" + _infoPersons + "\n"
                  + " Found:\n" + _existingPersons);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(5);
        _columnsCategories.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("color_hex", new TableInfo.Column("color_hex", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(1);
        _indicesCategories.add(new TableInfo.Index("index_categories_name", true, Arrays.asList("name"), Arrays.asList("ASC")));
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(com.voicejournal.app.data.local.db.entity.CategoryEntity).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsVoiceLogs = new HashMap<String, TableInfo.Column>(9);
        _columnsVoiceLogs.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceLogs.put("person_id", new TableInfo.Column("person_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceLogs.put("audio_file_name", new TableInfo.Column("audio_file_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceLogs.put("duration_ms", new TableInfo.Column("duration_ms", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceLogs.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceLogs.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceLogs.put("is_draft", new TableInfo.Column("is_draft", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceLogs.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceLogs.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVoiceLogs = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysVoiceLogs.add(new TableInfo.ForeignKey("persons", "CASCADE", "NO ACTION", Arrays.asList("person_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesVoiceLogs = new HashSet<TableInfo.Index>(2);
        _indicesVoiceLogs.add(new TableInfo.Index("index_voice_logs_person_id", false, Arrays.asList("person_id"), Arrays.asList("ASC")));
        _indicesVoiceLogs.add(new TableInfo.Index("index_voice_logs_created_at", false, Arrays.asList("created_at"), Arrays.asList("ASC")));
        final TableInfo _infoVoiceLogs = new TableInfo("voice_logs", _columnsVoiceLogs, _foreignKeysVoiceLogs, _indicesVoiceLogs);
        final TableInfo _existingVoiceLogs = TableInfo.read(db, "voice_logs");
        if (!_infoVoiceLogs.equals(_existingVoiceLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "voice_logs(com.voicejournal.app.data.local.db.entity.VoiceLogEntity).\n"
                  + " Expected:\n" + _infoVoiceLogs + "\n"
                  + " Found:\n" + _existingVoiceLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsVoiceLogCategories = new HashMap<String, TableInfo.Column>(2);
        _columnsVoiceLogCategories.put("voice_log_id", new TableInfo.Column("voice_log_id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceLogCategories.put("category_id", new TableInfo.Column("category_id", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVoiceLogCategories = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysVoiceLogCategories.add(new TableInfo.ForeignKey("voice_logs", "CASCADE", "NO ACTION", Arrays.asList("voice_log_id"), Arrays.asList("id")));
        _foreignKeysVoiceLogCategories.add(new TableInfo.ForeignKey("categories", "CASCADE", "NO ACTION", Arrays.asList("category_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesVoiceLogCategories = new HashSet<TableInfo.Index>(1);
        _indicesVoiceLogCategories.add(new TableInfo.Index("index_voice_log_categories_category_id", false, Arrays.asList("category_id"), Arrays.asList("ASC")));
        final TableInfo _infoVoiceLogCategories = new TableInfo("voice_log_categories", _columnsVoiceLogCategories, _foreignKeysVoiceLogCategories, _indicesVoiceLogCategories);
        final TableInfo _existingVoiceLogCategories = TableInfo.read(db, "voice_log_categories");
        if (!_infoVoiceLogCategories.equals(_existingVoiceLogCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "voice_log_categories(com.voicejournal.app.data.local.db.entity.VoiceLogCategoryCrossRef).\n"
                  + " Expected:\n" + _infoVoiceLogCategories + "\n"
                  + " Found:\n" + _existingVoiceLogCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsVoiceNotes = new HashMap<String, TableInfo.Column>(6);
        _columnsVoiceNotes.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceNotes.put("voice_log_id", new TableInfo.Column("voice_log_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceNotes.put("audio_file_name", new TableInfo.Column("audio_file_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceNotes.put("duration_ms", new TableInfo.Column("duration_ms", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceNotes.put("text_note", new TableInfo.Column("text_note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVoiceNotes.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVoiceNotes = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysVoiceNotes.add(new TableInfo.ForeignKey("voice_logs", "CASCADE", "NO ACTION", Arrays.asList("voice_log_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesVoiceNotes = new HashSet<TableInfo.Index>(1);
        _indicesVoiceNotes.add(new TableInfo.Index("index_voice_notes_voice_log_id", false, Arrays.asList("voice_log_id"), Arrays.asList("ASC")));
        final TableInfo _infoVoiceNotes = new TableInfo("voice_notes", _columnsVoiceNotes, _foreignKeysVoiceNotes, _indicesVoiceNotes);
        final TableInfo _existingVoiceNotes = TableInfo.read(db, "voice_notes");
        if (!_infoVoiceNotes.equals(_existingVoiceNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "voice_notes(com.voicejournal.app.data.local.db.entity.VoiceNoteEntity).\n"
                  + " Expected:\n" + _infoVoiceNotes + "\n"
                  + " Found:\n" + _existingVoiceNotes);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1c8d1cc116ddef49e6fc59ac53878200", "1af024c18b5b3b06ec14f2a501ef45e1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "persons","categories","voice_logs","voice_log_categories","voice_notes");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `persons`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `voice_logs`");
      _db.execSQL("DELETE FROM `voice_log_categories`");
      _db.execSQL("DELETE FROM `voice_notes`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PersonDao.class, PersonDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CategoryDao.class, CategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VoiceLogDao.class, VoiceLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VoiceLogCategoryDao.class, VoiceLogCategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VoiceNoteDao.class, VoiceNoteDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PersonDao personDao() {
    if (_personDao != null) {
      return _personDao;
    } else {
      synchronized(this) {
        if(_personDao == null) {
          _personDao = new PersonDao_Impl(this);
        }
        return _personDao;
      }
    }
  }

  @Override
  public CategoryDao categoryDao() {
    if (_categoryDao != null) {
      return _categoryDao;
    } else {
      synchronized(this) {
        if(_categoryDao == null) {
          _categoryDao = new CategoryDao_Impl(this);
        }
        return _categoryDao;
      }
    }
  }

  @Override
  public VoiceLogDao voiceLogDao() {
    if (_voiceLogDao != null) {
      return _voiceLogDao;
    } else {
      synchronized(this) {
        if(_voiceLogDao == null) {
          _voiceLogDao = new VoiceLogDao_Impl(this);
        }
        return _voiceLogDao;
      }
    }
  }

  @Override
  public VoiceLogCategoryDao voiceLogCategoryDao() {
    if (_voiceLogCategoryDao != null) {
      return _voiceLogCategoryDao;
    } else {
      synchronized(this) {
        if(_voiceLogCategoryDao == null) {
          _voiceLogCategoryDao = new VoiceLogCategoryDao_Impl(this);
        }
        return _voiceLogCategoryDao;
      }
    }
  }

  @Override
  public VoiceNoteDao voiceNoteDao() {
    if (_voiceNoteDao != null) {
      return _voiceNoteDao;
    } else {
      synchronized(this) {
        if(_voiceNoteDao == null) {
          _voiceNoteDao = new VoiceNoteDao_Impl(this);
        }
        return _voiceNoteDao;
      }
    }
  }
}
