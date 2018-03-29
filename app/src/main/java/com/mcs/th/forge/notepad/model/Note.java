package com.mcs.th.forge.notepad.model;


import java.util.Date;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {

    @PrimaryKey
    private Long id;
    private String title;
    private String body;
    private Date dateCreated;
    private Date dataModified;

    private static final String TAG_LOG = "#Note#";

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDataModified() {
        return dataModified;
    }

    public void setDataModified(Date dataModified) {
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

    /*public String getReadableModifiedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy - hh:mm a", Locale.getDefault());
        sdf.setTimeZone(getDataModified().getTimeZone());
        Date modifiedDate = getDataModified().getTime();
        return sdf.format(modifiedDate);
    }*/

    /*public String getReadableCreatedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy - h:mm a", Locale.getDefault());
        sdf.setTimeZone(getDateCreated().getTimeZone());
        Date modifiedDate = getDateCreated().getTime();
        return sdf.format(modifiedDate);
    }*/


}
