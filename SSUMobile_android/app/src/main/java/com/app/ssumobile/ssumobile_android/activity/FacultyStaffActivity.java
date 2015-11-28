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
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;
import com.app.ssumobile.ssumobile_android.models.FacStaffModel;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FacultyStaffActivity extends AppCompatActivity {

    String body;

    ArrayAdapter adapter;
    //JSONtoModelProvider jsonConverter = new JSONtoModelProvider();

    EditText inputSearch;

    ArrayList<FacStaffModel> contactsList = new ArrayList<>();
    ArrayList<DepartmentModel> departmentsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_view);

        inputSearch = (EditText) findViewById(R.id.input_search);

        adapter = new ArrayAdapter<>(this, R.layout.activity_listview, contactsList);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FacultyStaffActivity.this, FacStaffModelActivity.class);
                //based on item add info to intent
                FacStaffModel FSmodel = contactsList.get(position);
                Bundle FS = new Bundle();
                FS.putSerializable("FacStaffModel", FSmodel);
                intent.putExtras(FS);
                startActivity(intent);
            }

        });
        // Enable Search Filter for search logic
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                FacultyStaffActivity.this.adapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FacultyStaffActivity.this.adapter.getFilter().filter(s.toString());
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

        // Set the FacStaff Department to it's respective name
        Thread runner2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for( int i = 0; i < contactsList.size(); i++ ){
                    for( int j = 0; j < departmentsList.size(); j++){
                        if( contactsList.get(i).department.equals( departmentsList.get(j).id ) ) {
                            if (departmentsList.get(j).displayName != null)
                                contactsList.get(i).department = departmentsList.get(j).displayName;
                            else
                                contactsList.get(i).department = departmentsList.get(j).name;
                        }
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
            case R.id.Departments_Directory:
                Thread Drunner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent Dintent = new Intent(FacultyStaffActivity.this, DepartmentsActivity.class);
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
                        Intent Bintent = new Intent(FacultyStaffActivity.this, BuildingsActivity.class);
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
                        Intent Sintent = new Intent(FacultyStaffActivity.this, SchoolsActivity.class);
                        finish();
                        startActivity(Sintent);
                    }
                });
                Srunner.start();
                return true;

            case R.id.Faculty_Staff_Directory:
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
        parseOutEvents();
    }


    // parse out events from body
    private void parseOutEvents() throws org.json.JSONException {

        JSONObject myjson = new JSONObject(body);
        JSONArray dept_array = myjson.getJSONArray("Department");
        JSONArray the_json_array = myjson.getJSONArray("Person");

        for (int i = 0; i < the_json_array.length(); i++) {
            //JSONObject obj = the_json_array.getJSONObject(i);
            contactsList.add( convertPersonJSONtoModel(the_json_array.getJSONObject(i)) );
            adapter.notifyDataSetChanged(); // update cards
        }
        for(int i = 0; i < dept_array.length(); i++){
            //JSONObject obj = dept_array.getJSONObject(i);
            departmentsList.add( convertDeptJSONtoModel(dept_array.getJSONObject(i)) );
        }
    }

    // get attributes of event string into an event
    public FacStaffModel convertPersonJSONtoModel(JSONObject s) throws org.json.JSONException{
        FacStaffModel currentContact = new FacStaffModel();

        currentContact.office = s.getString("office");
        currentContact.Created = s.getString("Created");
        currentContact.site = s.getString("site");
        currentContact.Modified = s.getString("Modified");
        currentContact.phone = s.getString("phone");
        currentContact.id = s.getString("id");
        currentContact.building = s.getString("building");
        currentContact.firstName = s.getString("firstName");
        currentContact.title = s.getString("title");
        currentContact.lastName = s.getString("lastName");
        currentContact.department = s.getString("department");
        currentContact.email = s.getString("email");

        return currentContact;
    }

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
}
