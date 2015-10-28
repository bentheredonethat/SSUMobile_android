package com.app.ssumobile.ssumobile_android.service;

/**
 * Created by ben on 10/26/15.
 */
public class calendarEvent {
    private String SUMMARY;
    private String LOCATION;
    private String X_TRUMBA_FIELD_NAME;
    private String DTSTAMP;
    private String CATEGORIES;
    private String UID;

    public String getSUMMARY() {
        return SUMMARY;
    }

    public void setSUMMARY(String SUMMARY) {
        this.SUMMARY = SUMMARY;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getX_TRUMBA_FIELD_NAME() {
        return X_TRUMBA_FIELD_NAME;
    }

    public void setX_TRUMBA_FIELD_NAME(String x_TRUMBA_FIELD_NAME) {
        X_TRUMBA_FIELD_NAME = x_TRUMBA_FIELD_NAME;
    }

    public String getDTSTAMP() {
        return DTSTAMP;
    }

    public void setDTSTAMP(String DTSTAMP) {
        this.DTSTAMP = DTSTAMP;
    }

    public String getCATEGORIES() {
        return CATEGORIES;
    }

    public void setCATEGORIES(String CATEGORIES) {
        this.CATEGORIES = CATEGORIES;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
