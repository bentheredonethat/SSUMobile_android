package com.app.ssumobile.ssumobile_android.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.BuildingModel;
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;
import com.app.ssumobile.ssumobile_android.models.FacStaffModel;

import java.net.HttpURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class DepartmentsActivity extends AppCompatActivity {
    String body;

    ArrayAdapter adapter;
    //JSONtoModelProvider jsonConverter;

    ArrayList<DepartmentModel> contactsList = new ArrayList<>();
    ArrayList<FacStaffModel> facStaffList1 = new ArrayList<>();
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
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DepartmentsActivity.this, DepartmentModelActivity.class);
                //based on item add info to intent
                DepartmentModel Dmodel = (DepartmentModel) adapter.getItem(position);
                Bundle B = new Bundle();
                B.putSerializable("DepartmentModel", Dmodel);
                intent.putExtras(B);
                startActivity(intent);
            }
        });

        // get data into listview in the background
        new ProgressTask(DepartmentsActivity.this).execute();

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Enable Search Filter for search logic
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                DepartmentsActivity.this.adapter.getFilter().filter(s.toString());
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
        switch (item.getItemId()) {
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
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");  // optional default is GET
        con.setRequestProperty("User-Agent", USER_AGENT); //add request header
        con.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        body = response.toString();

    }


    // parse out events from body
    private void parseOutEvents() throws org.json.JSONException {
        System.out.println("in parseOutEvents()");

        JSONObject myjson = new JSONObject(body);
        JSONArray the_json_array = myjson.getJSONArray("Department");
        JSONArray building_json_array = myjson.getJSONArray("Building");
        JSONArray facStaff_json_array = myjson.getJSONArray("Person");
        for (int i = 0; i < the_json_array.length(); i++) {
            contactsList.add(convertDeptJSONtoModel(the_json_array.getJSONObject(i)));
        }
        for (int i = 0; i < building_json_array.length(); i++) {
            buildingList.add(convertBuildJSONtoModel(building_json_array.getJSONObject(i)));
        }
        for (int i = 0; i < facStaff_json_array.length(); i++) {
            facStaffList1.add(convertPersonJSONtoModel(facStaff_json_array.getJSONObject(i)));
        }
    }

    // get attributes of event string into an event
    public DepartmentModel convertDeptJSONtoModel(JSONObject s) throws org.json.JSONException {
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

    public BuildingModel convertBuildJSONtoModel(JSONObject s) throws org.json.JSONException {
        BuildingModel currentContact = new BuildingModel();

        currentContact.Created = s.getString("Created");
        currentContact.Modified = s.getString("Modified");
        currentContact.id = s.getString("id");
        currentContact.name = s.getString("name");
        currentContact.Deleted = s.getString("Deleted");

        return currentContact;
    }

    // get attributes of event string into an event
    public FacStaffModel convertPersonJSONtoModel(JSONObject s) throws org.json.JSONException {
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


    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;
        private DepartmentsActivity activity;

        public ProgressTask(DepartmentsActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        /** progress dialog to show user that the backup is processing. */

        /**
         * application context.
         */
        private Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            // after downloading and parsing
            for (int i = 0; i < buildingList.size(); ++i) {
                for (int j = 0; j < contactsList.size(); ++j) {
                    if (buildingList.get(i).id.equals(contactsList.get(j).building))
                        contactsList.get(j).buildingName = buildingList.get(i).name;
                }
            }
            for (int i = 0; i < facStaffList1.size(); ++i) {
                for (int k = 0; k < contactsList.size(); ++k) {
                    if (contactsList.get(k).id.equals(facStaffList1.get(i).department))
                        contactsList.get(k).getFacStaffList().add(facStaffList1.get(i));
                }
            }
            adapter.notifyDataSetChanged(); // update cards

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (success) {
                Toast.makeText(context, "Thanks for waiting!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        }


        private void trysendGet(){
            try {
                sendGet("https://moonlight.cs.sonoma.edu/ssumobile/1_0/directory");
            } catch (Throwable t) {
                System.out.println(t.getCause());
            }
        }

        private void tryparseOutEvents(){
            try {
                parseOutEvents();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        protected Boolean doInBackground(final String... args) {

            this.dialog.setMessage("Downloading data...");
            trysendGet();

            tryparseOutEvents();

            return true;
        }

    }
}
