package com.app.ssumobile.ssumobile_android.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.CalendarFormat;
import com.app.ssumobile.ssumobile_android.service.CalendarService;
import com.app.ssumobile.ssumobile_android.service.RestClient;
import com.app.ssumobile.ssumobile_android.service.calendarEvent;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class CalendarActivity extends FragmentActivity {

    String SERVICE_ENDPOINT = "http://localhost:3000";

    CaldroidFragment caldroidFragment;
    android.support.v4.app.FragmentTransaction t;

    CaldroidListener listener;

    RestClient restClient;

    CalendarService calendarService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set layout for activity
        setContentView(R.layout.activity_calendar);
        initializeCalendar();
        initializeListener();


        restClient = new RestClient();

        // connect to remote calendar api?
        testConnection();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the activity starts */
    public void testConnection() {

        RestAdapter eventAdapter = new RestAdapter.Builder()
                .setEndpoint("http://www.cs.sonoma.edu/~levinsky/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()))
                .build();
        CalendarService calendarService = eventAdapter.create(CalendarService.class); // get service

        calendarService.getEvents(new Callback<List<calendarEvent>>() {
            @Override
            public void success(List<calendarEvent> calendarEvents, Response response) {
                String x = null;
                for (calendarEvent event: calendarEvents){
                    System.out.
                            println("SUMMARY: " + event.getSUMMARY());
                }
                responseSuccess(response);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("xyz fail to get list");
                responseFailure(error);
            }
        });

    }

    public void responseSuccess(Response response){
        Toast.makeText(getBaseContext(), "xyz yes!", Toast.LENGTH_SHORT).show();
    }
    public void responseFailure(RetrofitError error){
        Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_SHORT).show();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void initializeCalendar(){
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar, caldroidFragment);
        t.commit();
    }

    public void initializeListener() {
        listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Toast.makeText(getApplicationContext(), date.toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + date.toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                Toast.makeText(getApplicationContext(),
                        "Caldroid view is created",
                        Toast.LENGTH_SHORT).show();
            }
        };
        caldroidFragment.setCaldroidListener(listener);
    }





}
