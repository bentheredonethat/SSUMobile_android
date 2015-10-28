package com.app.ssumobile.ssumobile_android.models;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ben on 10/26/15.
 */
public class calendarEvent implements Parcelable{
//20151011T235000
    public String Year() {return this.DTSTAMP.substring(0, 4);}
    public String Month() {return this.DTSTAMP.substring(5, 6);}
    public String Day() {return this.DTSTAMP.substring(7, 8);}
    public String Hour() {return this.DTSTAMP.substring(9, 10);}
    public String Minute() {return this.DTSTAMP.substring(11, 12);}

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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getDTSTAMP());
        dest.writeString(getSUMMARY());
        dest.writeString(getUID());
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
