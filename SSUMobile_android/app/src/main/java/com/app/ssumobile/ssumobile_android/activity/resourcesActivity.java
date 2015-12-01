package com.app.ssumobile.ssumobile_android.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.resourceModel;

import net.minidev.json.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class resourcesActivity extends AppCompatActivity {


    ArrayList<String> resourceslist = new ArrayList<>();
    ArrayAdapter resourceAdapter;
    ArrayList<resourceModel> resources = new ArrayList<>();

    String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        ListView sectionsListView = (ListView) findViewById(R.id.sections);
        resourceAdapter = new ArrayAdapter<>(this, R.layout.activity_listview, resourceslist);
        sectionsListView.setAdapter(resourceAdapter);
        sectionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final resourceModel r = resources.get(position);

                final AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                        .setTitle(r.name)
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                if (r.url != null){
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, r.url, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent myIntent = new Intent(((Dialog) dialog).getContext(), resourceToURL.class);
                            myIntent.putExtra("url", r.url);
                            startActivity(myIntent);

                        }
                    });
                }
                if (r.phone != null){
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, r.phone, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String message = "You need to activate Phone permissions for this app";
                            try {
                                if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                    final Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + r.phone));
                                    resourcesActivity.this.startActivity(callIntent);
                                }else{
                                    String[] CallPermissions = {"android.permission.CALL_PHONE"};
                                    int requestID = 1;
                                    requestPermissions(CallPermissions, requestID);
                                }
                            }catch( Throwable t){
                                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });

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
        net.minidev.json.JSONArray items = (net.minidev.json.JSONArray) jsonObject.get("Resource");




        for (Object current : items){
            net.minidev.json.JSONObject currentResource = (net.minidev.json.JSONObject) current;
            resources.add(convertJSONObjecttoResourceModel(currentResource));
            resourceslist.add((String)(( currentResource.get("name"))));
        }
        resourceAdapter.notifyDataSetChanged();


    }

    private resourceModel convertJSONObjecttoResourceModel(net.minidev.json.JSONObject obj){
        resourceModel r = new resourceModel();

        r.name = (String) obj.get("name");
        r.phone = (String) obj.get("phone");
        r.url = (String) obj.get("url");
        r.section_id = (Long) obj.get("section_id");
        r.id = (Long) obj.get("id");

        return r;
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

}
