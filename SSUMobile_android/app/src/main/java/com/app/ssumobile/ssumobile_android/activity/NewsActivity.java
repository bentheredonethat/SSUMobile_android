package com.app.ssumobile.ssumobile_android.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.adapters.calendarCardAdapter;
import com.app.ssumobile.ssumobile_android.adapters.newsCardAdapter;
import com.app.ssumobile.ssumobile_android.models.calendarEventModel;
import com.app.ssumobile.ssumobile_android.models.newsStoryModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsActivity extends AppCompatActivity {

    ArrayList<newsStoryModel> events = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        Context c = getApplicationContext();
        mLayoutManager = new LinearLayoutManager(c);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new newsCardAdapter(events); // specify an adapter
        mRecyclerView.setAdapter(mAdapter);

        Thread runner = new Thread(new Runnable(){
            public void run()  {
                try {
                    //sendGet(url + Year + Month + Day); // get selected date's info
                    sendGet("http://www.cs.sonoma.edu/~levinsky/mini_news.json");
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

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
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
    private void parseOutEvents() throws org.json.JSONException, ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(body);
        org.json.simple.JSONArray the_json_array = (org.json.simple.JSONArray) obj;
        for (int i = 0; i < the_json_array.size(); i++) {
            events.add(convertJSONtoStory(the_json_array.get(i)));
            mAdapter.notifyDataSetChanged(); // update cards
        }
    }

    // get attributes of event string into an event
    private newsStoryModel convertJSONtoStory(Object o) throws org.json.JSONException{
        org.json.simple.JSONObject s = (org.json.simple.JSONObject) o;
        newsStoryModel story = new newsStoryModel();

        story.Updated = (String) s.get("Updated");
        story.Title = (String) s.get("Title");
        story.Published = (String) s.get("Published");
        story.Link = (String) s.get("Link");
        story.Category = (String) s.get("Category");
        story.ID  = (String) s.get("ID");
        story.Content = (String) s.get("Content");
        story.Summary = (String) s.get("Summary");
        story.ImageURL = (String) s.get("ImageURL");

        return story;
    }


}
