package com.app.ssumobile.ssumobile_android.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.ContactModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DirActivity extends AppCompatActivity {

    Fragment fac_staff_frag = new FacultyStaffFragment();
    Fragment department_frag = new DepartmentsFragment();
    Fragment buildings_frag = new BuildingsFragment();
    Fragment schools_frag = new SchoolsFragment();
   

    String body;

    ArrayAdapter adapter;

    // Array of strings...
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X", "boop", "jhsodfs","booooop"};


    ArrayList<ContactModel> contactsList = new ArrayList<>();

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dir);


            adapter = new ArrayAdapter<ContactModel>(this, R.layout.activity_listview, contactsList);

            ListView listView = (ListView) findViewById(R.id.mobile_list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(DirActivity.this,ContactActivity.class);
                    //based on item add info to intent
                    startActivity(intent);
                }

            });



        }

    @Override
    protected void onStart(){
        super.onStart();
        Thread runner = new Thread(new Runnable(){
            public void run()  {
                try {
                    //sendGet(url + Year + Month + Day); // get selected date's info
                    sendGet("http://www.cs.sonoma.edu/~levinsky/mini_dir.json");
                } catch (Throwable t) {
                    System.out.println(t.getCause());
                }
            }
        });
        runner.start();
        try {
            runner.join();
            adapter.notifyDataSetChanged(); // update cards
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("in onstart()");
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
    private void parseOutEvents() throws org.json.JSONException {


        JSONObject myjson = new JSONObject(body);
        JSONArray the_json_array = myjson.getJSONArray("Department");
        for (int i = 0; i < the_json_array.length(); i++) {
            contactsList.add(convertDeptJSONtoContact(the_json_array.getJSONObject(i)));
            adapter.notifyDataSetChanged(); // update cards
        }
    }

    // get attributes of event string into an event
    private ContactModel convertDeptJSONtoContact(JSONObject s) throws org.json.JSONException{
        ContactModel currentContact = new ContactModel();

        currentContact.ac = s.getString("ac");
        currentContact.office = s.getString("office");
        currentContact.Created = s.getString("Created");
        currentContact.site = s.getString("site");
        currentContact.Modified = s.getString("Modified");
        currentContact.phone = s.getString("phone");
        currentContact.chair = s.getString("chair");
        currentContact.id = s.getString("id");
        currentContact.building = s.getString("building");
        currentContact.school = s.getString("school");
        currentContact.displayName = s.getString("displayName");
        currentContact.name = s.getString("name");
        currentContact.Deleted = s.getString("Deleted");

        return currentContact;
    }
}
