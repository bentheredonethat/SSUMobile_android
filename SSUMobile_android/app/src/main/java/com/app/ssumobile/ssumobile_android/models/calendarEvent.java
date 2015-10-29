package com.app.ssumobile.ssumobile_android.models;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ben on 10/26/15.
 */
public class calendarEvent implements Parcelable{


    public calendarEvent(Parcel in) {
        readFromParcel(in);
    }
    public calendarEvent() { ; };
    private String SUMMARY;
    private String LOCATION;
    private String X_TRUMBA_FIELD_NAME;
    private String DTSTAMP;
    private String CATEGORIES;
    private String UID;

    public String getSUMMARY() {
        return SUMMARY;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public String getX_TRUMBA_FIELD_NAME() {
        return X_TRUMBA_FIELD_NAME;
    }

    public String getDTSTAMP() {
        return DTSTAMP;
    }

    public String getCATEGORIES() {
        return CATEGORIES;
    }

    public String getUID() {
        return UID;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getDTSTAMP());
        dest.writeString(getSUMMARY());
        dest.writeString(getUID());
        dest.writeString(getCATEGORIES());
        dest.writeString(getX_TRUMBA_FIELD_NAME());
        dest.writeString(getLOCATION());
    }

    private void readFromParcel(Parcel in) {
// We just need to read back each
        // field in the order that it was
        // written to the parcel
        DTSTAMP = in.readString();
        SUMMARY = in.readString();
        UID = in.readString();
        CATEGORIES = in.readString();
        X_TRUMBA_FIELD_NAME = in.readString();
        LOCATION = in.readString();


    }

    public static final Parcelable.Creator<calendarEvent> CREATOR = new Parcelable.Creator<calendarEvent>() {

        public calendarEvent createFromParcel(Parcel in) {
            return new calendarEvent();
        }

        public calendarEvent[] newArray(int size) {
            return new calendarEvent[size];
        }
    };
}
