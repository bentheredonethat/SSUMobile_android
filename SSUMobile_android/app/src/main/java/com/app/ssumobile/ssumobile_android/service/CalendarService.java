package com.app.ssumobile.ssumobile_android.service;

import com.squareup.okhttp.Response;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;


/**
 * Created by ben on 10/21/15.
 */
public interface CalendarService {

    @GET("/VCALENDAR")
    void getHeaderInfo(Callback<Response> c);

//    @GET("/s.aspx?calendar=ssucalendar-performing_arts&date=20151105")
//    Observable<List<calendarEventModel>> getEvents();
    //@GET("/out.json")
    @GET("/s.aspx?calendar=ssucalendar-all-events")
    Observable<String> getAllEvents(@Query("date") String date);

//    @GET("/s.aspx?calendar=ssucalendar-all-events&widget=main   ")
//    Observable<List<calendarEventModel>> getAllEvents(@Query("date") String date,Callback<String> g);

    @GET("/s.aspx?calendar=ssucalendar-all-events&widget=main")
    void getAllEvents(@Query("date")String date, Callback<String> cb);

}

