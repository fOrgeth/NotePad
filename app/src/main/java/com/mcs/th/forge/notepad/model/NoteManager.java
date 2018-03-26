package com.mcs.th.forge.notepad.model;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.mcs.th.forge.notepad.MyApp;

import io.realm.Realm;
import io.realm.RealmResults;


public class NoteManager {

    private static NoteManager sNoteManagerInstance = null;
    private Realm realm;
    private RealmResults<Note> notes;

    private final String TAG_LOG = "NoteManager";


    public static NoteManager getInstance() {
        if (sNoteManagerInstance == null) {
            sNoteManagerInstance = new NoteManager();
        }
        return sNoteManagerInstance;
    }

    private NoteManager() {
        realm = Realm.getDefaultInstance();
        notes = realm.where(Note.class).findAllAsync();
    }


    public List<Note> getAllNotes() {
        List<Note> resultNotesList = new ArrayList<>();
        RealmResults<Note> notes = realm.where(Note.class).findAllAsync();
        resultNotesList.addAll(notes);
        return resultNotesList;
    }

//    public List<Note> getAllNotes() {
//        return MyApp.getDB().getAllNotes();
//    }

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

    public void create(final Note note) {
        Long id;
        if (realm.where(Note.class).max("id") == null) {
            id = 0L;
        } else {
            id = (long) realm.where(Note.class)
                    .max("id") + 1;
        }
        note.setId(id);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(note);
            }
        });
        Log.d(TAG_LOG, "Note's id: " + id);
    }

    public void delete(Note note){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Note> note = realm.where(Note.class).equalTo("id",1).findAll();
                note.deleteAllFromRealm();
            }
        });
    }
    /*public long create(Note note) {
        return MyApp.getDB()
                .addNote(note.getTitle(),
                        note.getBody(),
                        System.currentTimeMillis());
    }*/

    public void update(Note note) {
        MyApp.getDB()
                .updateNote(note.getTitle(),
                        note.getBody(),
                        System.currentTimeMillis(),
                        note.getId());

    }

    /*public void delete(Note note) {
        MyApp.getDB().deleteNote(note.getId());
    }*/

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        realm.close();
    }
}
