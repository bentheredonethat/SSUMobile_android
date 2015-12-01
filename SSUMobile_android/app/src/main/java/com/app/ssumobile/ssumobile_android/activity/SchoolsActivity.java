package com.app.ssumobile.ssumobile_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.SchoolModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class SchoolsActivity extends AppCompatActivity {
    String body;

    ArrayAdapter adapter;

    EditText inputSearch;

    ArrayList<SchoolModel> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_view);

        inputSearch = (EditText) findViewById(R.id.input_search);

        adapter = new ArrayAdapter<>(this, R.layout.activity_listview, contactsList);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SchoolsActivity.this, SchoolModelActivity.class);
                //based on item add info to intent
                Bundle B = new Bundle();
                SchoolModel school = (SchoolModel) adapter.getItem(position);
                B.putSerializable("SchoolModel", school);
                intent.putExtras(B);
                startActivity(intent);
            }
        });

        // Enable Search Filter for search logic
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                SchoolsActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SchoolsActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Thread runner = new Thread(new Runnable(){
            public void run()  {
                try {
                    //sendGet(url + Year + Month + Day); // get selected date's info
                    sendGet("https://moonlight.cs.sonoma.edu/ssumobile/1_0/directory.py");
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

        switch(item.getItemId()){
            case R.id.Faculty_Staff_Directory:
                Thread FSrunner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent FSintent = new Intent(SchoolsActivity.this, FacultyStaffActivity.class);
                        finish();
                        startActivity(FSintent);
                    }
                });
                FSrunner.start();
                return true;
            case R.id.Departments_Directory:
               Thread Drunner = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       Intent Dintent = new Intent(SchoolsActivity.this, DepartmentsActivity.class);
                       finish();
                       startActivity(Dintent);
                   }
               });
                Drunner.start();
                return true;
            case R.id.Buildings_Directory:
                Thread Brunner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent Bintent = new Intent(SchoolsActivity.this, BuildingsActivity.class);
                        finish();
                        startActivity(Bintent);
                    }
                });
                Brunner.start();
                return true;
            case R.id.Schools_Directory:
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // HTTP GET request
    private void sendGet(String url) throws Exception {

        final String USER_AGENT = "Mozilla/5.0";

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");  // optional default is GET
        con.setRequestProperty("User-Agent", USER_AGENT); //add request header
        con.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);


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
        JSONArray the_json_array = myjson.getJSONArray("School");
        for (int i = 0; i < the_json_array.length(); i++) {
            contactsList.add(convertJSONtoContact(the_json_array.getJSONObject(i)));
            adapter.notifyDataSetChanged(); // update cards
        }
    }

    // get attributes of event string into an event
    private SchoolModel convertJSONtoContact(JSONObject s) throws org.json.JSONException{
        SchoolModel currentSchool = new SchoolModel();

        currentSchool.building = s.getString("building");
        currentSchool.dean = s.getString("dean");
        currentSchool.admin = s.getString("admin");
        currentSchool.id = s.getString("id");
        currentSchool.name = s.getString("name");
        currentSchool.assistant= s.getString("assistant");

        return currentSchool;
    }
}

