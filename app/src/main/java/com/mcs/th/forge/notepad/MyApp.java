package com.mcs.th.forge.notepad;


import android.app.Application;

import com.mcs.th.forge.notepad.realm_db.RealmMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApp extends Application {

    public static final String LOG_TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("notepad.realm")
                .schemaVersion(3)
                .migration(new RealmMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfig);

    }

}
