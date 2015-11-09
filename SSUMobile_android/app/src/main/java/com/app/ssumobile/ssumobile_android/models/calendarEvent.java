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

    public void setSUMMARY(String SUMMARY) {
        this.SUMMARY = SUMMARY;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public void setX_TRUMBA_FIELD_NAME(String x_TRUMBA_FIELD_NAME) {
        X_TRUMBA_FIELD_NAME = x_TRUMBA_FIELD_NAME;
    }

    public void setDTSTAMP(String DTSTAMP) {
        this.DTSTAMP = DTSTAMP;
    }

    public void setCATEGORIES(String CATEGORIES) {
        this.CATEGORIES = CATEGORIES;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setSTYEAR(String STYEAR) {
        this.STYEAR = STYEAR;
    }

    public void setSTMONTH(String STMONTH) {
        this.STMONTH = STMONTH;
    }

    public void setSTDAY(String STDAY) {
        this.STDAY = STDAY;
    }

    public void setStHour(String stHour) {
        this.stHour = stHour;
    }

    public void setStMin(String stMin) {
        this.stMin = stMin;
    }

    private String X_TRUMBA_FIELD_NAME;
    private String DTSTAMP;
    private String CATEGORIES;
    private String UID;

    public String getSTYEAR() {
        return STYEAR;
    }

    public String getSTMONTH() {
        return STMONTH;
    }

    public String getSTDAY() {return STDAY;}

    public String getStHour() {
        return stHour;
    }

    public String getStMin() {
        return stMin;
    }

    public String getEndYear() {
        return endYear;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public String getEndDay() {
        return endDay;
    }

    public String getEndHour() {
        return endHour;
    }

    public String getEndMin() {
        return endMin;
    }

    private String STYEAR;
    private String STMONTH;
    private String STDAY;
    private String stHour;
    private String stMin;

    private String endYear;
    private String endMonth;
    private String endDay;
    private String endHour;
    private String endMin;

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
        dest.writeString(getSTYEAR());
        dest.writeString(getSTMONTH());
        dest.writeString(getSTDAY());
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
        STYEAR = in.readString();
        STMONTH = in.readString();
        STDAY = in.readString();
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
