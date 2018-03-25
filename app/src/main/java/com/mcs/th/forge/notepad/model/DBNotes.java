package com.mcs.th.forge.notepad.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.mcs.th.forge.notepad.MyApp;

import java.util.ArrayList;
import java.util.List;

public class DBNotes extends DBSQLite {

    private static final String SQL_WHERE_BY_ID = BaseColumns._ID + "=?";
    private static final String DB_NAME = "DBNotes.db";
    private static final int DB_VERSION = 1;
    private final String TAG_LOG = "#DBNotes#";

    public DBNotes(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBSQLite.execSQL(db, TableNotes.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DBSQLite.dropTable(db, TableNotes.T_NAME);
        this.onCreate(db);
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = this.getReadableCursor(TableNotes.T_NAME,
                new String[]{BaseColumns._ID,
                        TableNotes.C_TITLE,
                        TableNotes.C_BODY,
                        TableNotes.C_DATE},
                        null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                notes.add(Note.getNoteFromCursor(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        Log.d(TAG_LOG, "cursor returned = "+cursor);
        return notes;
    }

    /** what a shit???
    public Note getNote(Long id) {
        Note note;
        Cursor cursor = MyApp.getDB().getReadableDatabase().query("tNotes",
                new String[]{"_id", "TITLE", "BODY", "DATE"}, "_id = " + id,
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Log.d(TAG_LOG, "cursor = " + cursor.getString(cursor.getColumnIndex("TITLE")));
            note = Note.getNoteFromCursor(cursor);
            cursor.close();
            return note;
        }
        Log.d(TAG_LOG, "cursor returned = " + cursor);
        return null;
    }*/

    long addNote(String title, String body, long date){
        ContentValues values = new ContentValues();
        values.put(TableNotes.C_TITLE, title);
        values.put(TableNotes.C_BODY, body);
        values.put(TableNotes.C_DATE, date);
        return this.getWritableDatabase().insert(TableNotes.T_NAME, null, values);
    }

    boolean updateNote(String title, String body, long date, long id) {
        ContentValues values = new ContentValues();
        values.put(TableNotes.C_TITLE, title);
        values.put(TableNotes.C_BODY, body);
        values.put(TableNotes.C_DATE, date);
        return 1 == this.getWritableDatabase().update(TableNotes.T_NAME, values,
                SQL_WHERE_BY_ID, new String[]{String.valueOf(id)});

    }

    boolean deleteNote(long id) {
        return 1 == this.getWritableDatabase().delete(TableNotes.T_NAME,
                SQL_WHERE_BY_ID, new String[]{String.valueOf(id)});
    }


    /**
     * Public static class that contains information about table tNotes
     */
    static class TableNotes implements BaseColumns {
        /**
         * Name of this table.
         */
        static final String T_NAME = "tNotes";


        static final String C_TITLE = "TITLE";

        static final String C_BODY = "BODY";

        static final String C_DATE = "DATE";

        /**
         * SQL query for a create this table.
         */
        static final String SQL_CREATE = "CREATE TABLE " + T_NAME + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_TITLE + " TEXT NOT NULL," +
                C_BODY + " TEXT NOT NULL," +
                C_DATE + " TEXT NOT NULL)";
    }
}
