package com.mcs.th.forge.notepad.model;

import android.database.Cursor;
import android.util.Log;

import java.util.List;

import com.mcs.th.forge.notepad.MyApp;


public class NoteManager {
    //    private Context mContext;
    private static NoteManager sNoteManagerInstance = null;
    private final String TAG_LOG = "NoteManager";

    /*public static NoteManager getInstance(Context context) {
        if (sNoteManagerInstance == null) {
            sNoteManagerInstance = new NoteManager(context.getApplicationContext());
        }
        return sNoteManagerInstance;
    }*/

    public static NoteManager getInstance() {
        if (sNoteManagerInstance == null) {
            sNoteManagerInstance = new NoteManager();
        }
        return sNoteManagerInstance;
    }

    private NoteManager() {

    }

   /* public long create(Note note) {
        ContentValues values = new ContentValues();
        values.put(DBNotes.TableNotes.C_TITLE, note.getTitle());
        values.put(DBNotes.TableNotes.C_BODY, note.getBody());
        values.put(DBNotes.TableNotes.C_DATE, System.currentTimeMillis());
        return MyApp.getDB().addNote(values);
    }*/

    public List<Note> getAllNotes() {
        return MyApp.getDB().getAllNotes();
    }

    public Note getNote(Long id) {
        Note note;
        Cursor cursor = MyApp.getDB().getReadableDatabase().query("tNotes",
                new String[]{"_id", "TITLE", "BODY", "DATE"}, "_id = " + id,
                null, null, null, null);
        if (cursor == null) {
            return null;
        }
        cursor.moveToFirst();
        Log.d(TAG_LOG, "cursor = " + cursor.getString(cursor.getColumnIndex("TITLE")));
        note = Note.getNoteFromCursor(cursor);
        cursor.close();
        Log.d(TAG_LOG, "cursor returned = " + cursor);
        return note;
    }

    public long create(Note note) {
        return MyApp.getDB()
                .addNote(note.getTitle(),
                        note.getBody(),
                        System.currentTimeMillis());
    }

    public void update(Note note) {
        MyApp.getDB()
                .updateNote(note.getTitle(),
                        note.getBody(),
                        System.currentTimeMillis(),
                        note.getId());

    }

    public void delete(Note note) {
        MyApp.getDB().deleteNote(note.getId());
    }
}
