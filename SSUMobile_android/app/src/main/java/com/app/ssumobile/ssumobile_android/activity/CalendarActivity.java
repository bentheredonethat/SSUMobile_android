package com.app.ssumobile.ssumobile_android.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.service.CalendarService;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

<<<<<<< HEAD
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

=======
>>>>>>> master
public class CalendarActivity extends FragmentActivity {

    String SERVICE_ENDPOINT = "http://localhost:3000";

    CaldroidFragment caldroidFragment;
    android.support.v4.app.FragmentTransaction t;

    CaldroidListener listener;


    CalendarService calendarService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set layout for activity
        setContentView(R.layout.activity_calendar);
        initializeCalendar();
        initializeListener();

        // connect to remote calendar api?
        testConnection();

        System.out.println("xyz trying instantiate service");
        System.out.println("xyz instantiated service");

        System.out.println("xyz trying to get service");
        System.out.println("xyz got the service");



        calendarService.getHeaderInfo(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                System.out.println("xyz success in callback");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("xyz failure in callback");
            }
        });


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
        String message = "still no cnxn ";

        // if condition works then say so!
        if (connected){
            message = "got cnxn :)";
        }

        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
        Toast.makeText(getBaseContext(), getBaseContext().toString(), Toast.LENGTH_SHORT).show();
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
