package com.app.ssumobile.ssumobile_android.providers;

import com.app.ssumobile.ssumobile_android.models.BuildingModel;
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;
import com.app.ssumobile.ssumobile_android.models.FacStaffModel;
import com.app.ssumobile.ssumobile_android.models.SchoolModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by WestFlow on 10/5/2017.
 */
public class DataProvider {
    String Djson = null;
    String Fjson = null;
    String Bjson = null;
    String Sjson = null;
    JSONtoModelProvider jsonConverter = new JSONtoModelProvider();

    public  void getData(ArrayList<DepartmentModel> D, ArrayList<FacStaffModel> F, ArrayList<BuildingModel> B, ArrayList<SchoolModel> S){
        Thread runner1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Djson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/department/?format=json");
                Fjson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/person/?format=json");
                Bjson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/building/?format=json");
                Sjson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/school/?format=json");
            }
        });
        runner1.start();
        try{
            runner1.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        tryparseOutEvents(D, F, B, S);
    }

    private String sendGet(String url){
        try {
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

            return response.toString();
        } catch (Throwable t) {
            System.out.println(t.getCause());
        }
        return null;
    }
    // parse out events from body
    private void parseOutEvents(ArrayList<DepartmentModel> D, ArrayList<FacStaffModel> F, ArrayList<BuildingModel> B, ArrayList<SchoolModel> S) throws org.json.JSONException {
        System.out.println("in parseOutEvents()");

        JSONArray DepJSON = new JSONArray(Djson);
        JSONArray FacJSON = new JSONArray(Fjson);
        JSONArray BuildingJSON = new JSONArray(Bjson);
        JSONArray SJSON = new JSONArray(Sjson);

        for (int i = 0; i < DepJSON.length(); i++) {
            D.add(jsonConverter.convertDeptJSONtoModel(DepJSON.getJSONObject(i)));
        }
        for (int i = 0; i < FacJSON.length(); i++) {
            F.add(jsonConverter.convertPersonJSONtoModel(FacJSON.getJSONObject(i)));
        }
        for (int i = 0; i < BuildingJSON.length(); i++) {
            B.add(jsonConverter.convertBuildJSONtoModel(BuildingJSON.getJSONObject(i)));
        }
        for (int i = 0; i < SJSON.length(); i++) {
            S.add(jsonConverter.convertJSONtoSchool(SJSON.getJSONObject(i)));
        }
    }

    private void tryparseOutEvents(ArrayList<DepartmentModel> D, ArrayList<FacStaffModel> F, ArrayList<BuildingModel> B, ArrayList<SchoolModel> S){
        try {
            parseOutEvents(D,F,B,S);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
