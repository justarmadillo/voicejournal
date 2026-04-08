package com.voicejournal.app.data.local.db.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.voicejournal.app.data.local.db.entity.VoiceNoteEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VoiceNoteDao_Impl implements VoiceNoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VoiceNoteEntity> __insertionAdapterOfVoiceNoteEntity;

  private final EntityDeletionOrUpdateAdapter<VoiceNoteEntity> __deletionAdapterOfVoiceNoteEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByVoiceLogId;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public VoiceNoteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVoiceNoteEntity = new EntityInsertionAdapter<VoiceNoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `voice_notes` (`id`,`voice_log_id`,`audio_file_name`,`duration_ms`,`text_note`,`created_at`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VoiceNoteEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getVoiceLogId());
        if (entity.getAudioFileName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAudioFileName());
        }
        statement.bindLong(4, entity.getDurationMs());
        if (entity.getTextNote() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTextNote());
        }
        statement.bindLong(6, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfVoiceNoteEntity = new EntityDeletionOrUpdateAdapter<VoiceNoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `voice_notes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VoiceNoteEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByVoiceLogId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM voice_notes WHERE voice_log_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM voice_notes";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final VoiceNoteEntity voiceNote,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVoiceNoteEntity.insert(voiceNote);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final VoiceNoteEntity voiceNote,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfVoiceNoteEntity.handle(voiceNote);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByVoiceLogId(final String voiceLogId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByVoiceLogId.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, voiceLogId);
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
          __preparedStmtOfDeleteByVoiceLogId.release(_stmt);
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
  public Flow<List<VoiceNoteEntity>> getByVoiceLogId(final String voiceLogId) {
    final String _sql = "SELECT * FROM voice_notes WHERE voice_log_id = ? ORDER BY created_at ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, voiceLogId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"voice_notes"}, new Callable<List<VoiceNoteEntity>>() {
      @Override
      @NonNull
      public List<VoiceNoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfVoiceLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "voice_log_id");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audio_file_name");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_ms");
          final int _cursorIndexOfTextNote = CursorUtil.getColumnIndexOrThrow(_cursor, "text_note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<VoiceNoteEntity> _result = new ArrayList<VoiceNoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VoiceNoteEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpVoiceLogId;
            _tmpVoiceLogId = _cursor.getString(_cursorIndexOfVoiceLogId);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final String _tmpTextNote;
            if (_cursor.isNull(_cursorIndexOfTextNote)) {
              _tmpTextNote = null;
            } else {
              _tmpTextNote = _cursor.getString(_cursorIndexOfTextNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new VoiceNoteEntity(_tmpId,_tmpVoiceLogId,_tmpAudioFileName,_tmpDurationMs,_tmpTextNote,_tmpCreatedAt);
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
  public Flow<List<VoiceLogNoteCount>> getAllNoteCounts() {
    final String _sql = "SELECT voice_log_id, COUNT(*) as count FROM voice_notes GROUP BY voice_log_id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"voice_notes"}, new Callable<List<VoiceLogNoteCount>>() {
      @Override
      @NonNull
      public List<VoiceLogNoteCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfVoiceLogId = 0;
          final int _cursorIndexOfCount = 1;
          final List<VoiceLogNoteCount> _result = new ArrayList<VoiceLogNoteCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VoiceLogNoteCount _item;
            final String _tmpVoiceLogId;
            _tmpVoiceLogId = _cursor.getString(_cursorIndexOfVoiceLogId);
            final int _tmpCount;
            _tmpCount = _cursor.getInt(_cursorIndexOfCount);
            _item = new VoiceLogNoteCount(_tmpVoiceLogId,_tmpCount);
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
  public Object getAllSync(final Continuation<? super List<VoiceNoteEntity>> $completion) {
    final String _sql = "SELECT * FROM voice_notes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VoiceNoteEntity>>() {
      @Override
      @NonNull
      public List<VoiceNoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfVoiceLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "voice_log_id");
          final int _cursorIndexOfAudioFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "audio_file_name");
          final int _cursorIndexOfDurationMs = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_ms");
          final int _cursorIndexOfTextNote = CursorUtil.getColumnIndexOrThrow(_cursor, "text_note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<VoiceNoteEntity> _result = new ArrayList<VoiceNoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VoiceNoteEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpVoiceLogId;
            _tmpVoiceLogId = _cursor.getString(_cursorIndexOfVoiceLogId);
            final String _tmpAudioFileName;
            if (_cursor.isNull(_cursorIndexOfAudioFileName)) {
              _tmpAudioFileName = null;
            } else {
              _tmpAudioFileName = _cursor.getString(_cursorIndexOfAudioFileName);
            }
            final long _tmpDurationMs;
            _tmpDurationMs = _cursor.getLong(_cursorIndexOfDurationMs);
            final String _tmpTextNote;
            if (_cursor.isNull(_cursorIndexOfTextNote)) {
              _tmpTextNote = null;
            } else {
              _tmpTextNote = _cursor.getString(_cursorIndexOfTextNote);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new VoiceNoteEntity(_tmpId,_tmpVoiceLogId,_tmpAudioFileName,_tmpDurationMs,_tmpTextNote,_tmpCreatedAt);
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
}
