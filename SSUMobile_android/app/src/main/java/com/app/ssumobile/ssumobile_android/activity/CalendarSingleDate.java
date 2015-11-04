package com.app.ssumobile.ssumobile_android.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.adapters.CardAdapter;
import com.app.ssumobile.ssumobile_android.models.calendarEvent;
import com.app.ssumobile.ssumobile_android.service.CalendarService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CalendarSingleDate extends Activity {

    TextView t;
    ArrayList<calendarEvent> events = new ArrayList<>();

    RestAdapter restAdapter;
    CalendarService calendarService;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String Year, Month, Day = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_single_date);

        String dateString = getIntent().getStringExtra("dateString");
        setDateFields(dateString);



        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
         mRecyclerView.setHasFixedSize(true);
        Context c = getApplicationContext();
        mLayoutManager = new LinearLayoutManager(c);
        mRecyclerView.setLayoutManager(mLayoutManager);


        restAdapter = getEventAdapter();
        calendarService = restAdapter.create(CalendarService.class);

        setupService();
    }


    private void setupService(){
        calendarService.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<calendarEvent>, Observable<calendarEvent>>() {
                    @Override
                    public Observable<calendarEvent> call(List<calendarEvent> calendarEvents) {
                        return Observable.from(calendarEvents);
                    }
                })
                .filter(new Func1<calendarEvent, Boolean>() {
                    @Override
                    public Boolean call(calendarEvent calendarEvent) {
                        return true;
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        // specify an adapter
                        mAdapter = new CardAdapter(events);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    }
                })
                .subscribe(
                        new Action1<calendarEvent>() {
                            @Override
                            public void call(calendarEvent calendarEvent) {
                                events.add(calendarEvent); // right now unconditionally display events on page
                            }
                        }
                );
    }

    private void setDateFields(String dateStr){
        Date date = null;
        String format = "EEE MMM dd hh:mm:ss zzz yyyy";


        try {
            date = new SimpleDateFormat(format).parse(dateStr);
            Integer month = date.getMonth();
            Integer day = date.getDay();

            Year = dateStr.substring(24);
            Month = month.toString();
            Day = day.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar_single_date, menu);
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

    private RestAdapter getEventAdapter(){

        return  new RestAdapter.Builder()
                .setEndpoint("http://www.cs.sonoma.edu/~levinsky/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()))
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
