package com.app.ssumobile.ssumobile_android.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;

import java.lang.reflect.Field;

public class StartScreenActivity extends AppCompatActivity {

    Button CalendarButton;
    Button NewsButton;
    Button DirButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCalendarButton();
        setNewsButton();
        setDirButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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

    public void setCalendarButton(){

        Thread runner = new Thread(new Runnable() {
            public void run() {
                String message = "Error: Failed to set calendar button.";
                try {
                    // Locate the button in activity_main.xml
                    CalendarButton = (Button) findViewById(R.id.calendar_button);
                    // Capture button clicks
                    CalendarButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {

                            // Start NewActivity.class
                            Intent myIntent = new Intent(StartScreenActivity.this,
                                    CalendarActivity.class);
                            startActivity(myIntent);
                        }
                    });
                } catch (Throwable t) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        runner.start();

    }

    public void setNewsButton(){
        // Locate the button in activity_main.xml
        NewsButton = (Button) findViewById(R.id.news_button);
        // Capture button clicks
        NewsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(StartScreenActivity.this,
                        NewsActivity.class);
                startActivity(myIntent);
            }
        });

    }

    public void setDirButton(){
        Thread DirThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Locate the button in activity_main.xml
                DirButton = (Button) findViewById(R.id.dir_button);
                // Capture button clicks
                DirButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {

                        // Start DirActivity.class
                        Intent myIntent = new Intent(StartScreenActivity.this,
                                DirActivity.class);
                        startActivity(myIntent);
                    }
                });
            }
        });
        DirThread.start();
    }


}
