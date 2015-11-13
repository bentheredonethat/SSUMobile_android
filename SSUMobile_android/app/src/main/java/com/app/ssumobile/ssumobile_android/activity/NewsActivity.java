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
import com.app.ssumobile.ssumobile_android.models.newsStoryModel;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ArrayList<newsStoryModel> events = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        newsStoryModel test1 = new newsStoryModel();
        test1.description = "x";
        test1.title = "y";
        test1.publish_date = "z";
        events.add(test1);
        test1.description = "a";
        test1.title = "b";
        test1.publish_date = "c";
        events.add(test1);

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

    /** Called when the user clicks the Send button */
    public void testConnection(View view) {
        // Do something in response to button
        boolean connected = false;
        String message = "still no cnxn :(";
//
//        // Step 2: Load page from assets -- TO DO: add asset with html file that has js
//        webView.loadUrl("file:///android_asset/index.html");
//
//        // Step 3: Enable Javascript
//        webView.getSettings().setJavaScriptEnabled(true);


        // if condition works then say so!
        if (connected){
            message = "got cnxn :)";
        }
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();

    }

}
