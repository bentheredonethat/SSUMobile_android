package com.app.ssumobile.ssumobile_android.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.BuildingModel;
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;

/**
 * Created by Tephros on 11/20/2015.
 */
public class BuildingModelActivity extends AppCompatActivity {

    TextView BuildingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the Department_model_view to the current view
        setContentView(R.layout.building_model_view);
        // Set instances of each Button/Text View
        BuildingName = (TextView) findViewById(R.id.building_textview);


        Bundle data = getIntent().getExtras();
        BuildingModel building;
        building = (BuildingModel) data.getSerializable("BuildingModel");
        if( !building.name.isEmpty() )
            BuildingName.setText(building.name);

        // Initiate Threads for onClickListeners

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
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
