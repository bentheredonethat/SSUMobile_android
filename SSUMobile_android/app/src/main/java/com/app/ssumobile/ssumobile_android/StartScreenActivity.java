package com.app.ssumobile.ssumobile_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class StartScreenActivity extends AppCompatActivity {

    Button CalendarButton;
    Button NewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCalendarButton();
        setNewsButton();
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


}
