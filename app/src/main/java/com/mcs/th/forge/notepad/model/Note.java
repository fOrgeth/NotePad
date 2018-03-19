package com.mcs.th.forge.notepad.model;


import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Note {

    private Long id;
    private String title;
    private String body;
    private Calendar dateCreated;
    private Calendar dataModified;

    private static final String TAG_LOG = "#Note#";

    public String getReadableModifiedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy - hh:mm a", Locale.getDefault());
        sdf.setTimeZone(getDataModified().getTimeZone());
        Date modifiedDate = getDataModified().getTime();
        return sdf.format(modifiedDate);
    }

    public String getReadableCreatedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy - h:mm a", Locale.getDefault());
        sdf.setTimeZone(getDateCreated().getTimeZone());
        Date modifiedDate = getDateCreated().getTime();
        return sdf.format(modifiedDate);
    }

    public static Note getNoteFromCursor(Cursor cursor) {
        Note note = new Note();
        if (cursor == null) {
            Log.d(TAG_LOG, "cursor = null!!! " + cursor);
        }
        note.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        note.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
        note.setBody(cursor.getString(cursor.getColumnIndex("BODY")));

        /*Calendar calendar = GregorianCalendar.getInstance();

        calendar.setTimeInMillis(cursor.getLong(cursor.getColumnIndex("DATE")));
        note.setDateCreated(calendar);*/

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(cursor.getLong(cursor.getColumnIndex("DATE")));
        note.setDateCreated(calendar);
        return note;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Calendar getDataModified() {
        return dataModified;
    }

    public void setDataModified(Calendar dataModified) {
        this.dataModified = dataModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}
