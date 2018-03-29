package com.mcs.th.forge.notepad;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApp extends Application {

    /* Private field for store a LOG tag */
    public static final String LOG_TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("notepad.realm")
                .build();
        Realm.setDefaultConfiguration(realmConfig);

    }

}
