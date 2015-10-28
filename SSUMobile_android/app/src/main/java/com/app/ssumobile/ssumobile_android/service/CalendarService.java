package com.app.ssumobile.ssumobile_android.service;

import android.provider.ContactsContract;

import com.app.ssumobile.ssumobile_android.models.CalendarFormat;
import com.squareup.okhttp.Response;

import net.fortuna.ical4j.model.Calendar;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import rx.Observable;


/**
 * Created by ben on 10/21/15.
 */
public interface CalendarService {

    @GET("/VCALENDAR")
    void getHeaderInfo(Callback<retrofit.client.Response> c);


    @GET("/VEVENT")
    List<calendarEvent> getEvents();

    //@GET("/VEVENT")
    @GET("/mini_events.json")
    void getEvents(Callback<List<calendarEvent>> g);

}

