package com.app.ssumobile.ssumobile_android;

import android.app.Dialog;
import android.content.res.AssetManager;
import android.graphics.Path;
import android.widget.Toast;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;

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
    public CalendarDataHolder(AssetManager assetManager) {
        try {
            InputStream inputStream = assetManager.open("ssucalendar_community.ics");
            CalendarBuilder builder = new CalendarBuilder();
         //   calendar = builder.build(inputStream);
            System.out.println("xyz found file or dir");

        }
        catch(Exception e){
            System.out.print("stack trace: " + e.fillInStackTrace().toString());
            System.out.println("xyz could not find file or dir");
            }
    }



}
