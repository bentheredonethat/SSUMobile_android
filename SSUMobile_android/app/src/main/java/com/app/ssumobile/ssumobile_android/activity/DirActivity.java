package com.app.ssumobile.ssumobile_android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import com.app.ssumobile.ssumobile_android.R;

public class DirActivity extends AppCompatActivity {

    Button ContactButton, ContactButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir);
        setContactButton();
        setContactButton2();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dir, menu);
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

    public void setContactButton(){
        // Locate the button in activity_dir.xml
        ContactButton = (Button) findViewById(R.id.contact_button);
        ContactButton.setText("John Doe");
        // Capture button clicks
        ContactButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start ContactActivity.class
                Intent myIntent = new Intent(DirActivity.this,
                        ContactActivity.class);
                startActivity(myIntent);
            }

        });
    }
    public void setContactButton2(){
        // Locate the button in activity_dir.xml
        ContactButton2 = (Button) findViewById(R.id.contact_button2);
        ContactButton2.setText("John Doe");
        // Capture button clicks
        ContactButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start ContactActivity.class
                Intent myIntent = new Intent(DirActivity.this,
                        ContactActivity.class);
                startActivity(myIntent);
            }

        });
    }
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
