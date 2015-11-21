package com.app.ssumobile.ssumobile_android.models;

import java.io.Serializable;

/**
 * Created by Tephros on 11/18/2015.
 */
public class FacStaffModel implements Serializable {

        public String office;
        public String Created;
        public String site;
        public String Modified;
        public String phone;
        public String id;
        public String building;
        public String title;
        public String firstName;
        public String lastName;
        public String department;
        public String email;
        public String toString(){ return lastName + ", " + firstName ; }

}

