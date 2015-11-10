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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.RestAdapter;


public class CalendarSingleDate extends Activity {

    TextView t;
    ArrayList<calendarEvent> events = new ArrayList<>();

    RestAdapter restAdapter;
    CalendarService calendarService;

    final String url = "http://25livepub.collegenet.com/s.aspx?calendar=ssucalendar-all-events&widget=main&date=";

    String body;

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

    }

    private void setDateFields(String dateStr) {
        Date date = null;
        String format = "EEE MMM dd hh:mm:ss zzz yyyy";


        try {
            date = new SimpleDateFormat(format).parse(dateStr);
            Integer month = date.getMonth();

            Year = dateStr.substring(24);
            Month = month.toString();
            Day = dateStr.substring(8, 10);
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

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new CardAdapter(events); // specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        Thread runner = new Thread(new Runnable(){
            public void run()  {
                try {
                    sendGet(url + Year + Month + Day); // get selected date's info
                } catch (Throwable t) {
                    System.out.println(t.getCause());
                }
            }
        });
        runner.start();
        try {
            runner.join();
            mAdapter.notifyDataSetChanged(); // update cards
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("in onstart()");
    }

    // HTTP GET request
    private void sendGet(String url) throws Exception {

        final String USER_AGENT = "Mozilla/5.0";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");  // optional default is GET
        con.setRequestProperty("User-Agent", USER_AGENT); //add request header


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {response.append(inputLine);}
        in.close();

        body = response.toString();
        parseOutEvents();
    }

    // parse out events from body
    private void parseOutEvents() throws InterruptedException {
        String regexForOneEvent = "<tr class=\\\\\\\"twSimpleTableEventRow0 ebg0\\\\\\\">(.*?)<\\/tr>";

        final Matcher matcher = Pattern
                .compile(regexForOneEvent, Pattern.DOTALL)
                .matcher(body);
        while (matcher.find()){
            // parse out event from html text, then add to group!
            // IN PARALLEL!

            Thread runner = new Thread(new Runnable(){
                public void run()  {
                    events.add(parseHTMLTableRow(matcher.group()));
                }
            });
            runner.start();
            runner.join();
        }
        Collections.sort(events, new Comparator<calendarEvent>() {
            @Override
            public int compare(calendarEvent lhs, calendarEvent rhs) {
                return String.valueOf(lhs.getStartTime()).compareTo(rhs.getStartTime());
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    // get attributes of event string into an event
    private calendarEvent parseHTMLTableRow(String s){
        calendarEvent currentEvent = new calendarEvent();

        String startdateregex = "StartDate(.*?)<", locationregex = "twLocation(.*?)<",
                titleregex = "aria-level=\\\\\"6\\\\\">(.*?)<", eventidregex = "eventid=(.*?);",
                timeregex = "level=\\\\\\\"5\\\\\\\"\\>(.*?)\\<\\/span\\>";

        String startDateResult = "", locationResult = "", titleResult = "", eventIDResult = "", timeResult = "";

        // find start date
        Matcher findMe = Pattern.compile(startdateregex, Pattern.DOTALL).matcher(s);
        while (findMe.find()){
            startDateResult = findMe.group();
            startDateResult = startDateResult.substring(12);
            startDateResult = startDateResult.substring(0, startDateResult.length()-1);
        }

        // find location
        findMe = Pattern.compile(locationregex, Pattern.DOTALL).matcher(s);
        while (findMe.find()){
            locationResult = findMe.group();
            locationResult = locationResult.substring(13);
            locationResult = locationResult.substring(0, locationResult.length()-1);
        }
        // find title
        findMe = Pattern.compile(titleregex, Pattern.DOTALL).matcher(s);
        while (findMe.find()){
            titleResult = findMe.group();
            titleResult = titleResult.substring(17);
            titleResult = titleResult.substring(0,titleResult.length()-1);
        }
        // find event id
        findMe = Pattern.compile(eventidregex, Pattern.DOTALL).matcher(s);
        while (findMe.find()){
            eventIDResult = findMe.group();
            eventIDResult = eventIDResult.substring(8, eventIDResult.length()-5);}

        // find time
        findMe = Pattern.compile(timeregex, Pattern.DOTALL).matcher(s);
        while (findMe.find()) {
            timeResult = findMe.group();
            timeResult = timeResult.substring(12,timeResult.length()-7);

        }
        currentEvent.setLOCATION(locationResult);
        currentEvent.setSUMMARY(titleResult);
        currentEvent.setDTSTAMP(startDateResult);
        currentEvent.setStartTime(timeResult);
        return currentEvent;
    }

}
