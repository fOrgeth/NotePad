package com.mcs.th.forge.notepad.realm_db;

import java.util.Date;

import io.realm.DynamicRealm;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class RealmMigration implements io.realm.RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();

        if (oldVersion == 1) {
            final RealmObjectSchema userSchema = schema.get("Note");
            userSchema.removeField("dataModified");
            oldVersion++;
        }
        if (oldVersion == 2) {
            schema.get("Note")
                    .addField("dataModified", Date.class);
            oldVersion++;
        }
    }
}
