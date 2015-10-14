package com.app.ssumobile.ssumobile_android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

public class CalendarActivity extends Activity {

    //public WebView webView = new WebView(this);
    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set layout for activity
        setContentView(R.layout.activity_calendar);

        // init calendarview
        initializeCalendar();

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
        // Do something in response to button
        boolean connected = false;
        String message = "still no cnxn :(";

        // if condition works then say so!
        if (connected){
            message = "got cnxn :)";
        }
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void initializeCalendar(){
        calendar = (CalendarView)findViewById(R.id.calendar);

        // set whether to show week number
        calendar.setShowWeekNumber(false);

        // set first day of week (monday)
        calendar.setFirstDayOfWeek(2);

        // background color for selected week
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        // unfocused month color
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        // line separator between weeks
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        // set vertical bar shown at selected vertical date bar
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);

        // set listener to be notified upon selected date change
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
               //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }


}
