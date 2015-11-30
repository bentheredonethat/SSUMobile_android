package com.app.ssumobile.ssumobile_android.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;

import net.minidev.json.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class resourcesActivity extends AppCompatActivity {


    ArrayList<String> emergencycontacts = new ArrayList<>();
    ArrayAdapter emergencycontactsadapter;

    ArrayList<String> campuscontacts = new ArrayList<>();
    ArrayAdapter campuscontactsadapter;

    ArrayList<String> CulinaryServices = new ArrayList<>();
    ArrayAdapter CulinaryServicesadapter;

    ArrayList<String> Transportation = new ArrayList<>();
    ArrayAdapter Transportationadapter;

    ArrayList<String> weburls = new ArrayList<>();
    ArrayAdapter weburlsadapter;

    ListView culinaryListView;
    ListView emergencycontactslistView;
    ListView campuscontactslistView;
    ListView weburlsListView;
    ListView transportationlistView;

    String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        addHeaders();


        initEmergencyContacts();
        initCampusContacts();
        initCulinary();
        initTransportation();
        initWebURLS();

        Thread runner = new Thread(new Runnable(){
            public void run()  {
                try {
                    populateResources(); // get selected date's info
                } catch (Throwable t) {
                    System.out.println(t.getCause());
                }
            }
        });
        runner.start();
        try {
            runner.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void populateResources()throws Exception{


        final String USER_AGENT = "Mozilla/5.0";
        URL obj = new URL("https://moonlight.cs.sonoma.edu/ssumobile/1_0/resources");
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");  // optional default is GET
        con.setRequestProperty("User-Agent", USER_AGENT); //add request header
        con.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        body = response.toString();

        net.minidev.json.parser.JSONParser parser = new JSONParser(JSONParser.ACCEPT_USELESS_COMMA);
        Object o = parser.parse(body);
        net.minidev.json.JSONObject jsonObject = (net.minidev.json.JSONObject)o;
        net.minidev.json.JSONArray resources = (net.minidev.json.JSONArray) jsonObject.get("Resource");

        setEmergencycontactsadapter(resources);
        setCampuscontactsadapter(resources);
        setCulinaryServicesadapteradapter(resources);
        setTransportationadapter(resources);
        setWeburlsadapter(resources);
    }

    private void setEmergencycontactsadapter(net.minidev.json.JSONArray resources){
        for (Object currentResource : resources){
            net.minidev.json.JSONObject jsonResource = (net.minidev.json.JSONObject) currentResource;

            if ((Long)jsonResource.get("section_id") == 1) {
                String name = (String) jsonResource.get("name");
                emergencycontacts.add(name);
                emergencycontactsadapter.notifyDataSetChanged();
            }
        }
    }

    private void setCulinaryServicesadapteradapter(net.minidev.json.JSONArray resources){
        for (Object currentResource : resources){
            net.minidev.json.JSONObject jsonResource = (net.minidev.json.JSONObject) currentResource;

            if ((Long)jsonResource.get("section_id") == 3) {
                String name = (String) jsonResource.get("name");
                CulinaryServices.add(name);
                CulinaryServicesadapter.notifyDataSetChanged();
            }
        }
    }

    private void setCampuscontactsadapter(net.minidev.json.JSONArray resources){
        for (Object currentResource : resources){
            net.minidev.json.JSONObject jsonResource = (net.minidev.json.JSONObject) currentResource;

            if ((Long)jsonResource.get("section_id") == 2) {
                String name = (String) jsonResource.get("name");
                campuscontacts.add(name);
                campuscontactsadapter.notifyDataSetChanged();
            }
        }
    }

    private void setTransportationadapter(net.minidev.json.JSONArray resources){
        for (Object currentResource : resources){
            net.minidev.json.JSONObject jsonResource = (net.minidev.json.JSONObject) currentResource;

            if ((Long)jsonResource.get("section_id") == 4) {
                String name = (String) jsonResource.get("name");
                Transportation.add(name);
                Transportationadapter.notifyDataSetChanged();
            }
        }
    }

    private void setWeburlsadapter(net.minidev.json.JSONArray resources){
        for (Object currentResource : resources){
            net.minidev.json.JSONObject jsonResource = (net.minidev.json.JSONObject) currentResource;

            if ((Long)jsonResource.get("section_id") == 5) {
                String name = (String) jsonResource.get("name");
                weburls.add(name);
                weburlsadapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resources, menu);
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


    private void initEmergencyContacts(){
        emergencycontactsadapter = new ArrayAdapter<>(this, R.layout.activity_listview, emergencycontacts);
        emergencycontactslistView.setAdapter(emergencycontactsadapter);
        emergencycontactslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "hiii", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCampusContacts(){
        campuscontactsadapter = new ArrayAdapter<>(this, R.layout.activity_listview, campuscontacts);
        campuscontactslistView.setAdapter(campuscontactsadapter);
        campuscontactslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "hiii", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCulinary(){
        CulinaryServicesadapter = new ArrayAdapter<>(this, R.layout.activity_listview, CulinaryServices);
        culinaryListView.setAdapter(CulinaryServicesadapter);
        culinaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "hiii", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTransportation(){
        Transportationadapter = new ArrayAdapter<>(this, R.layout.activity_listview, Transportation);
        transportationlistView.setAdapter(Transportationadapter);
        transportationlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "hiii", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWebURLS(){
        weburlsadapter = new ArrayAdapter<>(this, R.layout.activity_listview, weburls);

        weburlsListView.setAdapter(weburlsadapter);
        weburlsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "hiii", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addHeaders(){

        weburlsListView = (ListView) findViewById(R.id.WebURLs);
        transportationlistView = (ListView) findViewById(R.id.Transportation);
        culinaryListView = (ListView) findViewById(R.id.CulinaryServices);
        emergencycontactslistView = (ListView) findViewById(R.id.EmergencyContacts);
        campuscontactslistView = (ListView) findViewById(R.id.CampusContacts);

        TextView textView = new TextView(this);
        textView.setBackgroundColor(Color.WHITE);
        textView.setAllCaps(true);
        textView.setTextColor(Color.parseColor("#001339"));



        textView.setText("Culinary Services");
        culinaryListView.addHeaderView(textView);

        textView.setText("Emergency Contacts");
        emergencycontactslistView.addHeaderView(textView);

        textView.setText("Campus Contacts");
        campuscontactslistView.addHeaderView(textView);

        textView.setText("Transportation");
        transportationlistView.addHeaderView(textView);

        textView.setText("Emergency Contacts");
        weburlsListView.addHeaderView(textView);


    }


}
