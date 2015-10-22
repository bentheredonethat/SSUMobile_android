package com.app.ssumobile.ssumobile_android;

import android.content.Context;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;

import java.io.InputStream;




/**
 * Created by ben on 10/15/15.
 */
public class CalendarDataHolder {
    InputStream fin;
    Calendar calendar;
    String calHeaderInfoURL = "http://localhost:3000/VCALENDAR";
    String calEventInfoURL = "http://localhost:3000/VEVENT";
    Context currentContext;

    public void setContext(Context c){
        currentContext = c;
    }

    public CalendarDataHolder() {
        try {

            CalendarBuilder builder = new CalendarBuilder();


            // hold json from url
            CalendarJSONClient calendarJSONClient = new CalendarJSONClient();

            CalendarFormatDataObject calendarFormatDataObj = new CalendarFormatDataObject();




          //  calendar = builder.build(inputStream);
            System.out.println("xyz found file or dir");

        }
        catch(Exception e){
            System.out.print("stack trace: " + e.fillInStackTrace().toString());
            System.out.println("xyz could not find file or dir");
            }
    }




}
