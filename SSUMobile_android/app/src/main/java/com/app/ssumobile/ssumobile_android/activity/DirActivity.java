package com.app.ssumobile.ssumobile_android.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import com.app.ssumobile.ssumobile_android.R;

public class DirActivity extends FragmentActivity {

    Fragment fac_staff_frag = new FacultyStaffFragment();
    Fragment department_frag = new DepartmentsFragment();
    Fragment buildings_frag = new BuildingsFragment();
    Fragment schools_frag = new SchoolsFragment();
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
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.Faculty_Staff_Directory:
                ft.replace(R.id.fragment_container, fac_staff_frag);
                ft.commit();
                return true;
            case R.id.Departments_Directory:
                ft.replace(R.id.fragment_container, department_frag);
                ft.commit();
                return true;
            case R.id.Buildings_Directory:
                ft.replace(R.id.fragment_container, buildings_frag);
                ft.commit();
                return true;
            case R.id.Schools_Directory:
                ft.replace(R.id.fragment_container, schools_frag);
                ft.commit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setContactButton(){
        Thread runner = new Thread(new Runnable() {
            @Override
            public void run() {
                // Locate the button in activity_dir.xml
                ContactButton = (Button) findViewById(R.id.contact_button);
                ContactButton.setText(getResources().getString(R.string.mockName));
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
        });
        runner.start();
    }

    public void setContactButton2(){
        Thread runner = new Thread(new Runnable() {
            @Override
            public void run() {
                // Locate the button in activity_dir.xml
                ContactButton2 = (Button) findViewById(R.id.contact_button2);
                ContactButton2.setText(getResources().getString(R.string.mockName));
                // Capture button clicks
                ContactButton2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        // Start ContactActivity.class
                        Intent myIntent = new Intent(DirActivity.this,
                                ContactActivity.class);
                        startActivity(myIntent);
                    }
                });
            }}
        );
        runner.start();
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
