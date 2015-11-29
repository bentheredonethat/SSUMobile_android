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
import com.app.ssumobile.ssumobile_android.models.BuildingModel;
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class DepartmentsActivity extends AppCompatActivity {
    String body;

    ArrayAdapter adapter;
    //JSONtoModelProvider jsonConverter;

    ArrayList<DepartmentModel> contactsList = new ArrayList<>();
    //ArrayList<FacStaffModel> facStaffList = new ArrayList<>();
    ArrayList<BuildingModel> buildingList = new ArrayList<>();

    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_view);
        // Set input search bar
        inputSearch = (EditText) findViewById(R.id.input_search);

        adapter = new ArrayAdapter<>(this, R.layout.activity_listview, contactsList);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DepartmentsActivity.this, DepartmentModelActivity.class);
                //based on item add info to intent
                DepartmentModel Dmodel = contactsList.get(position);
                Bundle B = new Bundle();
                B.putSerializable("DepartmentModel", Dmodel);
                intent.putExtras(B);
                startActivity(intent);
            }
        });

        // Enable Search Filter for search logic
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                DepartmentsActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DepartmentsActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
       Thread runner = new Thread(new Runnable(){
            public void run()  {
                try {
                    sendGet("http://www.cs.sonoma.edu/~wmitchel/master_dir.json");
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

        // Set the building name for each department

        Thread runner2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < buildingList.size(); i++) {
                    for (int j = 0; j < contactsList.size(); j++) {
                        if ( buildingList.get(i).id.equals( contactsList.get(j).building ) )
                            contactsList.get(j).buildingName = buildingList.get(i).name;
                    }
                }
            }
        });
        runner2.start();
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
                Thread runner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent FSintent = new Intent(DepartmentsActivity.this, FacultyStaffActivity.class);
                        finish();
                        startActivity(FSintent);
                    }
                });
                runner.start();
                return true;

            case R.id.Buildings_Directory:
               Thread Brunner = new Thread(new Runnable() {
                   @Override
                   public void run() {
                       Intent Bintent = new Intent(DepartmentsActivity.this, BuildingsActivity.class);
                       finish();
                       startActivity(Bintent);
                   }
               });
               Brunner.start();
                return true;

            case R.id.Schools_Directory:
                Thread Srunner = new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent Sintent = new Intent(DepartmentsActivity.this, SchoolsActivity.class);
                    finish();
                    startActivity(Sintent);
                }
            });
                Srunner.start();
                return true;

            case R.id.Departments_Directory:
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
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");  // optional default is GET
        con.setRequestProperty("User-Agent", USER_AGENT); //add request header


        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {response.append(inputLine);}
        in.close();

        body = response.toString();
        String test = body.substring(0, 15);
        System.out.println("in sendGet()");
        System.out.println(test);
        parseOutEvents();
    }


    // parse out events from body
    private void parseOutEvents() throws org.json.JSONException {
        System.out.println("in parseOutEvents()");

        Thread runner = new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                   JSONObject myjson = new JSONObject(body);
                   JSONArray the_json_array = myjson.getJSONArray("Department");
                   JSONArray building_json_array = myjson.getJSONArray("Building");
                   for (int i = 0; i < the_json_array.length(); i++) {
                       contactsList.add(convertDeptJSONtoModel(the_json_array.getJSONObject(i)));
                       adapter.notifyDataSetChanged(); // update cards
                   }
                   for (int i = 0; i < building_json_array.length(); i++) {
                       buildingList.add(convertBuildJSONtoModel(building_json_array.getJSONObject(i)));
                   }
               }catch( JSONException e){
                   throw new RuntimeException(e);
               }
            }
        });
        runner.start();
    }

    // get attributes of event string into an event
    public DepartmentModel convertDeptJSONtoModel(JSONObject s) throws org.json.JSONException{
        DepartmentModel currentContact = new DepartmentModel();

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

    public BuildingModel convertBuildJSONtoModel(JSONObject s) throws org.json.JSONException{
        BuildingModel currentContact = new BuildingModel();

        currentContact.Created = s.getString("Created");
        currentContact.Modified = s.getString("Modified");
        currentContact.id = s.getString("id");
        currentContact.name = s.getString("name");
        currentContact.Deleted = s.getString("Deleted");

        return currentContact;
    }
}
