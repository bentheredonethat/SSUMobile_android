package com.app.ssumobile.ssumobile_android.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by ben on 10/21/15.
 *
 *should have the following:
 * restclient object with your services
 * your gson object to deserialize responses
 * restAdapter
 */
public class RestClient {
    private static final String BASE_URL = "130.157.101.121:3000/";
    //private static final String BASE_URL = "http://130.157.101.231:3000/";
    private static CalendarService apiService;

    public RestClient()
    {
        System.out.println("xyz got into rest client()");

        Gson gson = new GsonBuilder()
               // .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL  )
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        apiService = restAdapter.create(CalendarService.class);

    }



    public CalendarService getCalendarService()
    {

        return apiService;
    }
}
