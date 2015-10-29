package com.app.ssumobile.ssumobile_android.service;

import com.app.ssumobile.ssumobile_android.providers.CalendarProvider;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by ben on 10/22/15.
 */
public class CalendarClient {
    private static CalendarProvider c;

    public static CalendarProvider getCalendarProvider(){
        RestAdapter r = new RestAdapter.Builder()
                .setEndpoint("localhost:3000")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()))
                .build();

        c = r.create(CalendarProvider.class);

        return c;
    }
}
