package com.app.ssumobile.ssumobile_android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.calendarEvent;

import java.io.Serializable;
import java.util.HashMap;

public class CalendarSingleDate extends AppCompatActivity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_single_date);
        t  = (TextView) findViewById(R.id.single_date_location);

        //Intent i = getIntent();
        //Bundle b = getIntent().getExtras();
        t.setText("less newbbbb");
    }

    @Override
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
        Intent i = this.getIntent();
        Bundle bundle = this.getIntent().getExtras();
        i.getBundleExtra("eventMap");
        t.setText("slightly less newbbbb");


    }

    static class DTSTAMPParser{
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
