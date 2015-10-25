package com.app.ssumobile.ssumobile_android.service;

import com.app.ssumobile.ssumobile_android.providers.CalendarProvider;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by ben on 10/22/15.
 */
public class CalendarClient {
    private static CalendarProvider c;

    public static CalendarProvider getCalendarProvider(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("localhost:3000")
                //.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        c = retrofit.create(CalendarProvider.class);

        return c;
    }
}
