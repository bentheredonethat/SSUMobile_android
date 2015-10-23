package com.app.ssumobile.ssumobile_android.providers;

import retrofit.http.GET;

/**
 * Created by ben on 10/21/15.
 */
public interface CalendarProvider {

    @GET("/VCALENDAR")
    void getHeaderInfo();


}

