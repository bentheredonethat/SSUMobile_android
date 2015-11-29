package com.app.ssumobile.ssumobile_android.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by ben on 10/26/15.
 */
public class calendarEventModel implements Parcelable{


    public String getStartsOn() {
        return StartsOn;
    }

    public void setStartsOn(String startsOn) {
        StartsOn = startsOn;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDeleted() {
        return Deleted;
    }

    public void setDeleted(String deleted) {
        Deleted = deleted;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEndsOn() {
        return EndsOn;
    }

    public void setEndsOn(String endsOn) {
        EndsOn = endsOn;
    }

    private String StartsOn;
    private String Description;
    private String Title;
    private String Deleted;
    private String Created;
    private String Location;
    private String Id;
    private String EndsOn;

    public calendarEventModel(Parcel in) {
        readFromParcel(in);
    }
    public calendarEventModel() { ; };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getStartsOn());
        dest.writeString(getDescription());
        dest.writeString(getTitle());
        dest.writeString(getDeleted());
        dest.writeString(getLocation());
        dest.writeString(getId());
        dest.writeString(getEndsOn());
    }

    private void readFromParcel(Parcel in) {
// We just need to read back each
        // field in the order that it was
        // written to the parcel
        StartsOn = in.readString();
        Description= in.readString();
        Title= in.readString();
        Deleted= in.readString();
        Created= in.readString();
        Location= in.readString();
        Id= in.readString();
        EndsOn= in.readString();
    }

    public static final Parcelable.Creator<calendarEventModel> CREATOR = new Parcelable.Creator<calendarEventModel>() {

        public calendarEventModel createFromParcel(Parcel in) {
            return new calendarEventModel();
        }

        public calendarEventModel[] newArray(int size) {
            return new calendarEventModel[size];
        }
    };

    public static Comparator<calendarEventModel> COMPARE_BY_START = new Comparator<calendarEventModel>() {
        public int compare(calendarEventModel one, calendarEventModel other) {
            return one.StartsOn.compareTo(other.StartsOn);
        }
    };

}
