package com.app.ssumobile.ssumobile_android.service;

import com.app.ssumobile.ssumobile_android.models.CalendarFormat;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import rx.Observable;

/**
 * Created by ben on 10/21/15.
 */
public interface CalendarService {
    String SERVICE_ENDPOINT = "http://localhost:3000";

    @GET("/VCALENDAR")
    public void getHeaderInfo();


}

