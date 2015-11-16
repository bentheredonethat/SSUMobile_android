package com.app.ssumobile.ssumobile_android.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;
import com.app.ssumobile.ssumobile_android.R;

public class DirActivity extends AppCompatActivity {

    Fragment fac_staff_frag = new FacultyStaffFragment();
    Fragment department_frag = new DepartmentsFragment();
    Fragment buildings_frag = new BuildingsFragment();
    Fragment schools_frag = new SchoolsFragment();
   

    // Array of strings...
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X", "boop", "jhsodfs","booooop"};

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dir);


            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);

            ListView listView = (ListView) findViewById(R.id.mobile_list);
            listView.setAdapter(adapter);

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
}
