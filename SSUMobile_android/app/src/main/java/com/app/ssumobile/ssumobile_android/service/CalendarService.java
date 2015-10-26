package com.app.ssumobile.ssumobile_android.service;

import com.app.ssumobile.ssumobile_android.models.CalendarFormat;
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
    void getHeaderInfo(Callback<retrofit.client.Response> c);
}

