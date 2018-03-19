package com.mcs.th.forge.notepad;


import android.app.Application;
import android.util.Log;

import com.mcs.th.forge.notepad.model.DBNotes;

public class MyApp extends Application {

    /* Static file for store a link to the data base object */
    private static DBNotes mDBNotes = null;

    /* Private field for store a LOG tag */
    public static final String LOG_TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();
        mDBNotes = new DBNotes(getApplicationContext());
    }

    public static DBNotes getDB() {
        return mDBNotes;
    }
}
