package com.app.ssumobile.ssumobile_android.activity;

import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

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
            DateString = Year + Month + Day;
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
            mAdapter.notifyDataSetChanged(); // update cards
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("in onstart()");
    }

    // HTTP GET request
    private void sendGet(String url) throws Exception {

        final String USER_AGENT = "Mozilla/5.0";

      //  URL obj = new URL(url);
        URL obj = new URL("http://www.cs.sonoma.edu/~levinsky/mini_events.json");
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
    private void parseOutEvents() throws org.json.JSONException {


        JSONObject myjson = new JSONObject(body);
        JSONArray the_json_array = myjson.getJSONArray("Event");
        for (int i = 0; i < the_json_array.length(); i++) {
            events.add(convertJSONtoEvent(the_json_array.getJSONObject(i)));
            mAdapter.notifyDataSetChanged(); // update cards
        }
    }

    // get attributes of event string into an event
    private calendarEventModel convertJSONtoEvent(JSONObject s) throws org.json.JSONException{
        calendarEventModel currentEvent = new calendarEventModel();

        currentEvent.setId(s.getString("Id"));
        currentEvent.setTitle(s.getString("Title"));
        currentEvent.setCreated(s.getString("Created"));
        currentEvent.setDeleted(s.getString("Deleted"));
        currentEvent.setStartsOn(s.getString("StartsOn"));
        currentEvent.setEndsOn(s.getString("EndsOn"));
        currentEvent.setLocation(s.getString("Location"));

        return currentEvent;
    }

    private static class MyTrustManager implements X509TrustManager
    {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }

    }
}
