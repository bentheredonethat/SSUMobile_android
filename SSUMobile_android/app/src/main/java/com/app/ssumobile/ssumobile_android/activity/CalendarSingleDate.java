package com.app.ssumobile.ssumobile_android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.adapters.calendarCardAdapter;
import com.app.ssumobile.ssumobile_android.models.calendarEventModel;
import com.app.ssumobile.ssumobile_android.service.CalendarService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import retrofit.RestAdapter;


public class CalendarSingleDate extends AppCompatActivity {




    TextView t;
    ArrayList<calendarEventModel> events = new ArrayList<>();

    RestAdapter restAdapter;
    CalendarService calendarService;

    final String url = "https://moonlight.cs.sonoma.edu/ssumobile/1_0/calendar.py";



    String body;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String Year, Month, Day, DateString = null;
    String currentdate;

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
            Integer month = date.getMonth() + 1;

            Year = dateStr.substring(24);
            Month = month.toString();
            Day = dateStr.substring(8, 10);
            DateString = Year + Month + Day;

            currentdate = new StringBuilder()
                    .append(Year).append("-").append(Month).append("-").append(Day)
                    .toString();

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
        mAdapter = new calendarCardAdapter(events); // specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        Thread runner = new Thread(new Runnable(){
            public void run()  {
                try {
                    sendGet(url); // get selected date's info
                   // sendGet("http://www.cs.sonoma.edu/~levinsky/mini_events.json");
                } catch (Throwable t) {
                    System.out.println(t.getCause());
                }
            }
        });
        runner.start();
        try {
            runner.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("in onstart()");
    }

    // HTTP GET request
    private void sendGet(String url) throws Exception {

        final String USER_AGENT = "Mozilla/5.0";
        URL obj = new URL(url);
       // URL obj = new URL("http://www.cs.sonoma.edu/~levinsky/mini_events.json");
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");  // optional default is GET
        con.setRequestProperty("User-Agent", USER_AGENT); //add request header
        con.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
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


        Boolean hasEvents = Boolean.FALSE;

        String[] parsedBody = body.split("\\}\\, \\{");
        for (int i = 0; i < parsedBody.length; i++){

            if (parsedBody[i].contains(currentdate)){
                events.add(stringToEvent(parsedBody[i]));
                hasEvents = Boolean.TRUE;
            }

        }

        if (hasEvents == Boolean.FALSE){
            Intent singleDateIntent = new Intent(CalendarSingleDate.this, CalendarActivity.class);
            singleDateIntent.putExtra("noEvent", true);
            startActivity(singleDateIntent); // put intent with event map in activity

        }


        Collections.sort(events, calendarEventModel.COMPARE_BY_START);

        mAdapter.notifyDataSetChanged(); // update cards for user

    }





    static final String REstartson = "StartsOn\\\"\\:\\s\\\"(.*?)\\\"";
    static final String REdescription = "Description\\\"\\:\\s\\\"(.*?)\\\"";
    static final String REtitle = "Title\\\"\\:\\s\\\"(.*?)\\\"";
    static final String REendson = "EndsOn\\\"\\:\\s\\\"(.*?)\\\"";
    static final String RElocation = "Location\\\"\\:\\s\\\"(.*?)\\\"";

    private calendarEventModel stringToEvent(String text){
        calendarEventModel c = new calendarEventModel();

        Matcher m;

        String insertMe;

        m = Pattern.compile(REstartson).matcher(text); // startson
        if (m.find()){
            insertMe = m.group(1);
            c.setStartsOn(insertMe);
        }
        m = Pattern.compile(REdescription).matcher(text); // description
        if (m.find()) {
            insertMe = m.group(1);
            c.setDescription(insertMe);
        }
        m = Pattern.compile(REtitle).matcher(text); // title
        if (m.find()) {
            insertMe = m.group(1);
            c.setTitle(insertMe);
        }
        m = Pattern.compile(RElocation).matcher(text); // location
        if (m.find()) {
            insertMe = m.group(1);
            c.setLocation(insertMe);
        }
        m = Pattern.compile(REendson).matcher(text); // endson
        if (m.find()) {
            insertMe = m.group(1);
            c.setEndsOn(insertMe);
        }

        return c;
    }


}
