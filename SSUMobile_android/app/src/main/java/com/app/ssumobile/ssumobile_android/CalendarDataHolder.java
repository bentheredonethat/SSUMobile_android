package com.app.ssumobile.ssumobile_android;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Path;
import android.widget.Toast;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import static java.security.AccessController.getContext;




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

            // get header info
            calendarJSONClient.getWebpage(calHeaderInfoURL, currentContext.getApplicationContext());



          //  calendar = builder.build(inputStream);
            System.out.println("xyz found file or dir");

        }
        catch(Exception e){
            System.out.print("stack trace: " + e.fillInStackTrace().toString());
            System.out.println("xyz could not find file or dir");
            }
    }




}
