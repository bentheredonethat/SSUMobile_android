package com.app.ssumobile.ssumobile_android.models;
import com.google.gson.annotations.SerializedName;


/**
 * Created by WestFlow on 10/16/2015.
 */
// An application model used to drive the Directory Activity view
public class ContactModel {

    @SerializedName("firstName")
    private String Fname;

    @SerializedName("lastName")
    private String Lname;

    @SerializedName("title")
    private String title;

    @SerializedName("phone")
    private String phone_num;

    @SerializedName("email")
    private String email;

    @SerializedName("department")
    private String department;

    @SerializedName("office")
    private String office;

    public String getFname() { return Fname; }

    public String getLname() { return Lname; }

    public String getTitle() { return title; }

    public String getPhone_num() { return phone_num; }

    public String getEmail() { return phone_num; }

    public String getDepartment() { return department; }

    public String getOffice() { return office; }
}
