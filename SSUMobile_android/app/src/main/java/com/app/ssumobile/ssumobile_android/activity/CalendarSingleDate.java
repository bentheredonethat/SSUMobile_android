package com.app.ssumobile.ssumobile_android.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.adapters.CardAdapter;
import com.app.ssumobile.ssumobile_android.models.calendarEvent;
import com.app.ssumobile.ssumobile_android.service.CalendarService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CalendarSingleDate extends AppCompatActivity {

    TextView t;
    ArrayList<calendarEvent> events = new ArrayList<>();

    RestAdapter restAdapter;
    CalendarService calendarService;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_single_date);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
         mRecyclerView.setHasFixedSize(true);


        Context c = getApplicationContext();

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(c);
        mRecyclerView.setLayoutManager(mLayoutManager);


        restAdapter = getEventAdapter();
        calendarService = restAdapter.create(CalendarService.class);

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
                                events.add(calendarEvent);
                            }
                        }
                );



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


//        ArrayList<String> myDataset = new ArrayList<>();
//        for (calendarEvent event : events){
//            myDataset.add(event.getSUMMARY());
//        }
//
//
//        // specify an adapter (see also next example)
//        mAdapter = new CardAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);
       // t.setText(events.get(0).getSUMMARY());



    }

     class DTSTAMPParser{
        //20151011T235000
        private String DTSTAMP;

        DTSTAMPParser(String DTSTAMP){
            this.DTSTAMP = DTSTAMP;
        }

        public String Year() {return this.DTSTAMP.substring(0, 4);}
        public String Month() {return this.DTSTAMP.substring(5, 6);}
        public String Day() {return this.DTSTAMP.substring(7, 8);}
        public String Hour() {return this.DTSTAMP.substring(9, 10);}
        public String Minute() {return this.DTSTAMP.substring(11, 12);}

    }

}
