package com.voicejournal.app.data.local.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.voicejournal.app.data.local.db.entity.CategoryEntity;
import com.voicejournal.app.data.local.db.entity.VoiceLogEntity;
import com.voicejournal.app.data.local.db.relation.CategoryCount;
import com.voicejournal.app.data.local.db.relation.VoiceLogWithCategories;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VoiceLogDao_Impl implements VoiceLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VoiceLogEntity> __insertionAdapterOfVoiceLogEntity;

  private final EntityDeletionOrUpdateAdapter<VoiceLogEntity> __deletionAdapterOfVoiceLogEntity;

  private final EntityDeletionOrUpdateAdapter<VoiceLogEntity> __updateAdapterOfVoiceLogEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateNotes;

  private final SharedSQLiteStatement __preparedStmtOfFinalizeDraft;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public VoiceLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVoiceLogEntity = new EntityInsertionAdapter<VoiceLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `voice_logs` (`id`,`person_id`,`context_id`,`audio_file_name`,`duration_ms`,`title`,`notes`,`is_draft`,`created_at`,`updated_at`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VoiceLogEntity entity) {
        statement.bindString(1, entity.getId());
        if (entity.getPersonId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPersonId());
        }
        if (entity.getContextId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContextId());
        }
        statement.bindString(4, entity.getAudioFileName());
        statement.bindLong(5, entity.getDurationMs());
        if (entity.getTitle() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTitle());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getNotes());
        }
        final int _tmp = entity.isDraft() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfVoiceLogEntity = new EntityDeletionOrUpdateAdapter<VoiceLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `voice_logs` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VoiceLogEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfVoiceLogEntity = new EntityDeletionOrUpdateAdapter<VoiceLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `voice_logs` SET `id` = ?,`person_id` = ?,`context_id` = ?,`audio_file_name` = ?,`duration_ms` = ?,`title` = ?,`notes` = ?,`is_draft` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VoiceLogEntity entity) {
        statement.bindString(1, entity.getId());
        if (entity.getPersonId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPersonId());
        }
        if (entity.getContextId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContextId());
        }
        statement.bindString(4, entity.getAudioFileName());
        statement.bindLong(5, entity.getDurationMs());
        if (entity.getTitle() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTitle());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getNotes());
        }
        final int _tmp = entity.isDraft() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getUpdatedAt());
        statement.bindString(11, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateNotes = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE voice_logs SET notes = ?, updated_at = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfFinalizeDraft = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE voice_logs SET person_id = ?, context_id = ?, notes = ?, is_draft = 0, updated_at = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM voice_logs WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM voice_logs";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final VoiceLogEntity voiceLog,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVoiceLogEntity.insert(voiceLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final VoiceLogEntity voiceLog,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfVoiceLogEntity.handle(voiceLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final VoiceLogEntity voiceLog,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfVoiceLogEntity.handle(voiceLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateNotes(final String id, final String notes, final long updatedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateNotes.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, notes);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateNotes.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object finalizeDraft(final String id, final String personId, final String contextId,
      final String notes, final long updatedAt, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfFinalizeDraft.acquire();
        int _argIndex = 1;
        if (personId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, personId);
        }
        _argIndex = 2;
        if (contextId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, contextId);
        }
        _argIndex = 3;
        if (notes == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, notes);
        }
        _argIndex = 4;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 5;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfFinalizeDraft.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<VoiceLogWithCategories>> getAllWithCategories() {
    final String _sql = "SELECT * FROM voice_logs ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"voice_log_categories", "categories",
        "voice_logs"}, new Callable<List<VoiceLogWithCategories>>() {
      @Override
      @NonNull
      public List<VoiceLogWithCategories> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfPersonId = CursorUtil.getColumnIndexOrThrow(_cursor, "person_id");
            final int _cursorIndexOfContextId = CursorUtil.getColumnIndexOrThrow(_cursor, "context_id");
            final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audio_file_name");
            final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_ms");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfIsDraft = CursorUtil.getColumnIndexOrThrow(_cursor, "is_draft");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final ArrayMap<String, ArrayList<CategoryEntity>> _collectionCategories = new ArrayMap<String, ArrayList<CategoryEntity>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfId);
              if (!_collectionCategories.containsKey(_tmpKey)) {
                _collectionCategories.put(_tmpKey, new ArrayList<CategoryEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcategoriesAscomVoicejournalAppDataLocalDbEntityCategoryEntity(_collectionCategories);
            final List<VoiceLogWithCategories> _result = new ArrayList<VoiceLogWithCategories>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final VoiceLogWithCategories _item;
              final VoiceLogEntity _tmpVoiceLog;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpPersonId;
              if (_cursor.isNull(_cursorIndexOfPersonId)) {
                _tmpPersonId = null;
              } else {
                _tmpPersonId = _cursor.getString(_cursorIndexOfPersonId);
              }
              final String _tmpContextId;
              if (_cursor.isNull(_cursorIndexOfContextId)) {
                _tmpContextId = null;
              } else {
                _tmpContextId = _cursor.getString(_cursorIndexOfContextId);
              }
              final String _tmpAudioFileName;
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
              final long _tmpDurationMs;
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpNotes;
              if (_cursor.isNull(_cursorIndexOfNotes)) {
                _tmpNotes = null;
              } else {
                _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
              }
              final boolean _tmpIsDraft;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfIsDraft);
              _tmpIsDraft = _tmp != 0;
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              _tmpVoiceLog = new VoiceLogEntity(_tmpId,_tmpPersonId,_tmpContextId,_tmpAudioFileName,_tmpDurationMs,_tmpTitle,_tmpNotes,_tmpIsDraft,_tmpCreatedAt,_tmpUpdatedAt);
              final ArrayList<CategoryEntity> _tmpCategoriesCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              _tmpCategoriesCollection = _collectionCategories.get(_tmpKey_1);
              _item = new VoiceLogWithCategories(_tmpVoiceLog,_tmpCategoriesCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<VoiceLogWithCategories>> getRecentLogs(final int limit) {
    final String _sql = "SELECT * FROM voice_logs ORDER BY created_at DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"voice_log_categories", "categories",
        "voice_logs"}, new Callable<List<VoiceLogWithCategories>>() {
      @Override
      @NonNull
      public List<VoiceLogWithCategories> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfPersonId = CursorUtil.getColumnIndexOrThrow(_cursor, "person_id");
            final int _cursorIndexOfContextId = CursorUtil.getColumnIndexOrThrow(_cursor, "context_id");
            final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audio_file_name");
            final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_ms");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfIsDraft = CursorUtil.getColumnIndexOrThrow(_cursor, "is_draft");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final ArrayMap<String, ArrayList<CategoryEntity>> _collectionCategories = new ArrayMap<String, ArrayList<CategoryEntity>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfId);
              if (!_collectionCategories.containsKey(_tmpKey)) {
                _collectionCategories.put(_tmpKey, new ArrayList<CategoryEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcategoriesAscomVoicejournalAppDataLocalDbEntityCategoryEntity(_collectionCategories);
            final List<VoiceLogWithCategories> _result = new ArrayList<VoiceLogWithCategories>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final VoiceLogWithCategories _item;
              final VoiceLogEntity _tmpVoiceLog;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpPersonId;
              if (_cursor.isNull(_cursorIndexOfPersonId)) {
                _tmpPersonId = null;
              } else {
                _tmpPersonId = _cursor.getString(_cursorIndexOfPersonId);
              }
              final String _tmpContextId;
              if (_cursor.isNull(_cursorIndexOfContextId)) {
                _tmpContextId = null;
              } else {
                _tmpContextId = _cursor.getString(_cursorIndexOfContextId);
              }
              final String _tmpAudioFileName;
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
              final long _tmpDurationMs;
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpNotes;
              if (_cursor.isNull(_cursorIndexOfNotes)) {
                _tmpNotes = null;
              } else {
                _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
              }
              final boolean _tmpIsDraft;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfIsDraft);
              _tmpIsDraft = _tmp != 0;
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              _tmpVoiceLog = new VoiceLogEntity(_tmpId,_tmpPersonId,_tmpContextId,_tmpAudioFileName,_tmpDurationMs,_tmpTitle,_tmpNotes,_tmpIsDraft,_tmpCreatedAt,_tmpUpdatedAt);
              final ArrayList<CategoryEntity> _tmpCategoriesCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              _tmpCategoriesCollection = _collectionCategories.get(_tmpKey_1);
              _item = new VoiceLogWithCategories(_tmpVoiceLog,_tmpCategoriesCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<VoiceLogWithCategories> getByIdWithCategories(final String id) {
    final String _sql = "SELECT * FROM voice_logs WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"voice_log_categories", "categories",
        "voice_logs"}, new Callable<VoiceLogWithCategories>() {
      @Override
      @Nullable
      public VoiceLogWithCategories call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfPersonId = CursorUtil.getColumnIndexOrThrow(_cursor, "person_id");
            final int _cursorIndexOfContextId = CursorUtil.getColumnIndexOrThrow(_cursor, "context_id");
            final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audio_file_name");
            final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_ms");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfIsDraft = CursorUtil.getColumnIndexOrThrow(_cursor, "is_draft");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final ArrayMap<String, ArrayList<CategoryEntity>> _collectionCategories = new ArrayMap<String, ArrayList<CategoryEntity>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfId);
              if (!_collectionCategories.containsKey(_tmpKey)) {
                _collectionCategories.put(_tmpKey, new ArrayList<CategoryEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcategoriesAscomVoicejournalAppDataLocalDbEntityCategoryEntity(_collectionCategories);
            final VoiceLogWithCategories _result;
            if (_cursor.moveToFirst()) {
              final VoiceLogEntity _tmpVoiceLog;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpPersonId;
              if (_cursor.isNull(_cursorIndexOfPersonId)) {
                _tmpPersonId = null;
              } else {
                _tmpPersonId = _cursor.getString(_cursorIndexOfPersonId);
              }
              final String _tmpContextId;
              if (_cursor.isNull(_cursorIndexOfContextId)) {
                _tmpContextId = null;
              } else {
                _tmpContextId = _cursor.getString(_cursorIndexOfContextId);
              }
              final String _tmpAudioFileName;
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
              final long _tmpDurationMs;
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpNotes;
              if (_cursor.isNull(_cursorIndexOfNotes)) {
                _tmpNotes = null;
              } else {
                _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
              }
              final boolean _tmpIsDraft;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfIsDraft);
              _tmpIsDraft = _tmp != 0;
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              _tmpVoiceLog = new VoiceLogEntity(_tmpId,_tmpPersonId,_tmpContextId,_tmpAudioFileName,_tmpDurationMs,_tmpTitle,_tmpNotes,_tmpIsDraft,_tmpCreatedAt,_tmpUpdatedAt);
              final ArrayList<CategoryEntity> _tmpCategoriesCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              _tmpCategoriesCollection = _collectionCategories.get(_tmpKey_1);
              _result = new VoiceLogWithCategories(_tmpVoiceLog,_tmpCategoriesCollection);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<VoiceLogWithCategories>> getByPersonId(final String personId) {
    final String _sql = "SELECT * FROM voice_logs WHERE person_id = ? ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, personId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"voice_log_categories", "categories",
        "voice_logs"}, new Callable<List<VoiceLogWithCategories>>() {
      @Override
      @NonNull
      public List<VoiceLogWithCategories> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfPersonId = CursorUtil.getColumnIndexOrThrow(_cursor, "person_id");
            final int _cursorIndexOfContextId = CursorUtil.getColumnIndexOrThrow(_cursor, "context_id");
            final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audio_file_name");
            final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_ms");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfIsDraft = CursorUtil.getColumnIndexOrThrow(_cursor, "is_draft");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final ArrayMap<String, ArrayList<CategoryEntity>> _collectionCategories = new ArrayMap<String, ArrayList<CategoryEntity>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfId);
              if (!_collectionCategories.containsKey(_tmpKey)) {
                _collectionCategories.put(_tmpKey, new ArrayList<CategoryEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcategoriesAscomVoicejournalAppDataLocalDbEntityCategoryEntity(_collectionCategories);
            final List<VoiceLogWithCategories> _result = new ArrayList<VoiceLogWithCategories>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final VoiceLogWithCategories _item;
              final VoiceLogEntity _tmpVoiceLog;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpPersonId;
              if (_cursor.isNull(_cursorIndexOfPersonId)) {
                _tmpPersonId = null;
              } else {
                _tmpPersonId = _cursor.getString(_cursorIndexOfPersonId);
              }
              final String _tmpContextId;
              if (_cursor.isNull(_cursorIndexOfContextId)) {
                _tmpContextId = null;
              } else {
                _tmpContextId = _cursor.getString(_cursorIndexOfContextId);
              }
              final String _tmpAudioFileName;
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
              final long _tmpDurationMs;
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpNotes;
              if (_cursor.isNull(_cursorIndexOfNotes)) {
                _tmpNotes = null;
              } else {
                _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
              }
              final boolean _tmpIsDraft;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfIsDraft);
              _tmpIsDraft = _tmp != 0;
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              _tmpVoiceLog = new VoiceLogEntity(_tmpId,_tmpPersonId,_tmpContextId,_tmpAudioFileName,_tmpDurationMs,_tmpTitle,_tmpNotes,_tmpIsDraft,_tmpCreatedAt,_tmpUpdatedAt);
              final ArrayList<CategoryEntity> _tmpCategoriesCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              _tmpCategoriesCollection = _collectionCategories.get(_tmpKey_1);
              _item = new VoiceLogWithCategories(_tmpVoiceLog,_tmpCategoriesCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<CategoryCount>> getCategoryStatsForPerson(final String personId) {
    final String _sql = "\n"
            + "        SELECT c.name as categoryName, c.color_hex as categoryColorHex, COUNT(*) as count\n"
            + "        FROM voice_logs vl\n"
            + "        JOIN voice_log_categories vlc ON vl.id = vlc.voice_log_id\n"
            + "        JOIN categories c ON vlc.category_id = c.id\n"
            + "        WHERE vl.person_id = ?\n"
            + "        GROUP BY c.id\n"
            + "        ORDER BY count DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, personId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"voice_logs",
        "voice_log_categories", "categories"}, new Callable<List<CategoryCount>>() {
      @Override
      @NonNull
      public List<CategoryCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategoryName = 0;
          final int _cursorIndexOfCategoryColorHex = 1;
          final int _cursorIndexOfCount = 2;
          final List<CategoryCount> _result = new ArrayList<CategoryCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryCount _item;
            final String _tmpCategoryName;
            _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            final String _tmpCategoryColorHex;
            if (_cursor.isNull(_cursorIndexOfCategoryColorHex)) {
              _tmpCategoryColorHex = null;
            } else {
              _tmpCategoryColorHex = _cursor.getString(_cursorIndexOfCategoryColorHex);
            }
            final int _tmpCount;
            _tmpCount = _cursor.getInt(_cursorIndexOfCount);
            _item = new CategoryCount(_tmpCategoryName,_tmpCategoryColorHex,_tmpCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<VoiceLogWithCategories>> getByContextId(final String contextId) {
    final String _sql = "SELECT * FROM voice_logs WHERE context_id = ? ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, contextId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"voice_log_categories", "categories",
        "voice_logs"}, new Callable<List<VoiceLogWithCategories>>() {
      @Override
      @NonNull
      public List<VoiceLogWithCategories> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfPersonId = CursorUtil.getColumnIndexOrThrow(_cursor, "person_id");
            final int _cursorIndexOfContextId = CursorUtil.getColumnIndexOrThrow(_cursor, "context_id");
            final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audio_file_name");
            final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_ms");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfIsDraft = CursorUtil.getColumnIndexOrThrow(_cursor, "is_draft");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
            final ArrayMap<String, ArrayList<CategoryEntity>> _collectionCategories = new ArrayMap<String, ArrayList<CategoryEntity>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfId);
              if (!_collectionCategories.containsKey(_tmpKey)) {
                _collectionCategories.put(_tmpKey, new ArrayList<CategoryEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcategoriesAscomVoicejournalAppDataLocalDbEntityCategoryEntity(_collectionCategories);
            final List<VoiceLogWithCategories> _result = new ArrayList<VoiceLogWithCategories>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final VoiceLogWithCategories _item;
              final VoiceLogEntity _tmpVoiceLog;
              final String _tmpId;
              _tmpId = _cursor.getString(_cursorIndexOfId);
              final String _tmpPersonId;
              if (_cursor.isNull(_cursorIndexOfPersonId)) {
                _tmpPersonId = null;
              } else {
                _tmpPersonId = _cursor.getString(_cursorIndexOfPersonId);
              }
              final String _tmpContextId;
              if (_cursor.isNull(_cursorIndexOfContextId)) {
                _tmpContextId = null;
              } else {
                _tmpContextId = _cursor.getString(_cursorIndexOfContextId);
              }
              final String _tmpAudioFileName;
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
              final long _tmpDurationMs;
              _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpNotes;
              if (_cursor.isNull(_cursorIndexOfNotes)) {
                _tmpNotes = null;
              } else {
                _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
              }
              final boolean _tmpIsDraft;
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfIsDraft);
              _tmpIsDraft = _tmp != 0;
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              _tmpVoiceLog = new VoiceLogEntity(_tmpId,_tmpPersonId,_tmpContextId,_tmpAudioFileName,_tmpDurationMs,_tmpTitle,_tmpNotes,_tmpIsDraft,_tmpCreatedAt,_tmpUpdatedAt);
              final ArrayList<CategoryEntity> _tmpCategoriesCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              _tmpCategoriesCollection = _collectionCategories.get(_tmpKey_1);
              _item = new VoiceLogWithCategories(_tmpVoiceLog,_tmpCategoriesCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<CategoryCount>> getCategoryStatsForContext(final String contextId) {
    final String _sql = "\n"
            + "        SELECT c.name as categoryName, c.color_hex as categoryColorHex, COUNT(*) as count\n"
            + "        FROM voice_logs vl\n"
            + "        JOIN voice_log_categories vlc ON vl.id = vlc.voice_log_id\n"
            + "        JOIN categories c ON vlc.category_id = c.id\n"
            + "        WHERE vl.context_id = ?\n"
            + "        GROUP BY c.id\n"
            + "        ORDER BY count DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, contextId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"voice_logs",
        "voice_log_categories", "categories"}, new Callable<List<CategoryCount>>() {
      @Override
      @NonNull
      public List<CategoryCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategoryName = 0;
          final int _cursorIndexOfCategoryColorHex = 1;
          final int _cursorIndexOfCount = 2;
          final List<CategoryCount> _result = new ArrayList<CategoryCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryCount _item;
            final String _tmpCategoryName;
            _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            final String _tmpCategoryColorHex;
            if (_cursor.isNull(_cursorIndexOfCategoryColorHex)) {
              _tmpCategoryColorHex = null;
            } else {
              _tmpCategoryColorHex = _cursor.getString(_cursorIndexOfCategoryColorHex);
            }
            final int _tmpCount;
            _tmpCount = _cursor.getInt(_cursorIndexOfCount);
            _item = new CategoryCount(_tmpCategoryName,_tmpCategoryColorHex,_tmpCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllSync(final Continuation<? super List<VoiceLogEntity>> $completion) {
    final String _sql = "SELECT * FROM voice_logs";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VoiceLogEntity>>() {
      @Override
      @NonNull
      public List<VoiceLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPersonId = CursorUtil.getColumnIndexOrThrow(_cursor, "person_id");
          final int _cursorIndexOfContextId = CursorUtil.getColumnIndexOrThrow(_cursor, "context_id");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audio_file_name");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_ms");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsDraft = CursorUtil.getColumnIndexOrThrow(_cursor, "is_draft");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<VoiceLogEntity> _result = new ArrayList<VoiceLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VoiceLogEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpPersonId;
            if (_cursor.isNull(_cursorIndexOfPersonId)) {
              _tmpPersonId = null;
            } else {
              _tmpPersonId = _cursor.getString(_cursorIndexOfPersonId);
            }
            final String _tmpContextId;
            if (_cursor.isNull(_cursorIndexOfContextId)) {
              _tmpContextId = null;
            } else {
              _tmpContextId = _cursor.getString(_cursorIndexOfContextId);
            }
            final String _tmpAudioFileName;
            _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsDraft;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDraft);
            _tmpIsDraft = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new VoiceLogEntity(_tmpId,_tmpPersonId,_tmpContextId,_tmpAudioFileName,_tmpDurationMs,_tmpTitle,_tmpNotes,_tmpIsDraft,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipcategoriesAscomVoicejournalAppDataLocalDbEntityCategoryEntity(
      @NonNull final ArrayMap<String, ArrayList<CategoryEntity>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipcategoriesAscomVoicejournalAppDataLocalDbEntityCategoryEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `categories`.`id` AS `id`,`categories`.`name` AS `name`,`categories`.`color_hex` AS `color_hex`,`categories`.`created_at` AS `created_at`,`categories`.`updated_at` AS `updated_at`,_junction.`voice_log_id` FROM `voice_log_categories` AS _junction INNER JOIN `categories` ON (_junction.`category_id` = `categories`.`id`) WHERE _junction.`voice_log_id` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      // _junction.voice_log_id;
      final int _itemKeyIndex = 5;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfName = 1;
      final int _cursorIndexOfColorHex = 2;
      final int _cursorIndexOfCreatedAt = 3;
      final int _cursorIndexOfUpdatedAt = 4;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_itemKeyIndex);
        final ArrayList<CategoryEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final CategoryEntity _item_1;
          final String _tmpId;
          _tmpId = _cursor.getString(_cursorIndexOfId);
          final String _tmpName;
          _tmpName = _cursor.getString(_cursorIndexOfName);
          final String _tmpColorHex;
          if (_cursor.isNull(_cursorIndexOfColorHex)) {
            _tmpColorHex = null;
          } else {
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
          }
          final long _tmpCreatedAt;
          _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
          _item_1 = new CategoryEntity(_tmpId,_tmpName,_tmpColorHex,_tmpCreatedAt,_tmpUpdatedAt);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
