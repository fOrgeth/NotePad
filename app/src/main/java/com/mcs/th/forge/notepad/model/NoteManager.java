package com.mcs.th.forge.notepad.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class NoteManager {

    private static NoteManager sNoteManagerInstance = null;
    private Realm realm;

    private final String TAG_LOG = "NoteManager";


    public static NoteManager getInstance() {
        if (sNoteManagerInstance == null) {
            sNoteManagerInstance = new NoteManager();
        }
        return sNoteManagerInstance;
    }

    private NoteManager() {
        realm = Realm.getDefaultInstance();
//        notes = realm.where(Note.class).findAllAsync();
    }


    public List<Note> getAllNotes() {
        List<Note> resultNotesList = new ArrayList<>();
        RealmResults<Note> notes = realm.where(Note.class).findAllAsync();
        resultNotesList.addAll(notes);
        return resultNotesList;
    }

    public Note getNote(Long id) {
        return realm.where(Note.class).equalTo("id", id).findFirst();
    }

    /*public void create(Note note) {
        Long id;
        if (realm.where(Note.class).max("id") == null) {
            id = 0L;
        } else {
            id = (long) realm.where(Note.class)
                    .max("id") + 1;
        }
        realm.beginTransaction();
        note.setId(id);
        realm.copyToRealm(note);
        realm.commitTransaction();
    }*/

    public void create(String title, String body) {
        Long id;
        if (realm.where(Note.class).max("id") == null) {
            id = 0L;
        } else {
            id = (long) realm.where(Note.class)
                    .max("id") + 1;
        }
        Note note = new Note();
        realm.beginTransaction();
        note.setId(id);
        note.setTitle(title);
        note.setBody(body);
        realm.copyToRealm(note);
        realm.commitTransaction();
    }

    public void update(String title, String body, Long id) {
        Note noteUpdate = realm.where(Note.class).equalTo("id", id).findFirst();
        realm.beginTransaction();
        noteUpdate.setTitle(title);
        noteUpdate.setBody(body);
//        noteUpdate.setDateCreated(note.getDateCreated());
        realm.commitTransaction();
    }

    public void delete(@NonNull Long id) {
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Note> note = realm.where(Note.class).equalTo("id", id).findAll();
                note.deleteAllFromRealm();
            }
        });*/
        realm.beginTransaction();
        RealmResults<Note> note = realm.where(Note.class).equalTo("id", id).findAll();
        note.deleteAllFromRealm();
        realm.commitTransaction();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        realm.close();
    }
}
