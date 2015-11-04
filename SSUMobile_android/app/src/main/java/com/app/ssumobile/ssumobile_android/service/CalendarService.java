package com.app.ssumobile.ssumobile_android.service;

import com.app.ssumobile.ssumobile_android.models.calendarEvent;
import com.squareup.okhttp.Response;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import rx.Observable;


/**
 * Created by ben on 10/21/15.
 */
public interface CalendarService {

    @GET("/VCALENDAR")
    void getHeaderInfo(Callback<Response> c);


//    @GET("/mini_events.json")
//    List<calendarEvent> getEvents();

    //@GET("/VEVENT")
    @GET("/mini_events.json")
    void getEvents(Callback<List<calendarEvent>> g);

    @GET("/out.json")
    Observable<List<calendarEvent>> getEvents();

}

