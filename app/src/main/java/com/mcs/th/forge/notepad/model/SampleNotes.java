package com.mcs.th.forge.notepad.model;

import java.util.ArrayList;
import java.util.Calendar;


import io.realm.Realm;

public class SampleNotes {
    private static Realm realm;

    public static void fillSampleNotes() {
        realm = Realm.getDefaultInstance();
        for (int i = 0; i < 5; i++) {
            final long y = realm.where(Note.class).findAll().last().getId() + 1;
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Note myNote = new Note();
                    myNote.setId(y);
                    myNote.setTitle("Note #" + y);
                    myNote.setBody("Body note #" + y);
                    myNote.setDateCreated(Calendar.getInstance().getTime().toString());
                    realm.copyToRealmOrUpdate(myNote);
                }
            });

        }
        realm.close();
    }

}
