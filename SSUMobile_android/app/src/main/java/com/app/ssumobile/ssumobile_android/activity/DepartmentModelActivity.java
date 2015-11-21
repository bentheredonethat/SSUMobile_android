package com.app.ssumobile.ssumobile_android.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;


/**
 * Created by WestFlow on 10/22/2015.
 */

public class DepartmentModelActivity extends AppCompatActivity {

    TextView DisplayName;
    Button PhoneButton;
    Button WebSite;
    Button OfficeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the Department_model_view to the current view
        setContentView(R.layout.department_model_view);
        // Set instances of each Button/Text View
        DisplayName = (TextView) findViewById(R.id.DisplayName_button);
        PhoneButton = (Button) findViewById(R.id.Phone_button);
        WebSite = (Button) findViewById(R.id.webSite_button);
        OfficeButton = (Button) findViewById(R.id.office_button);


        Bundle data = getIntent().getExtras();
        ContactProvider((DepartmentModel) data.getSerializable("DepartmentModel"));
        // Initiate Threads for onClickListeners
        PhoneButtonThread();

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

    public void ContactProvider(DepartmentModel d) {
        if( !d.office.equals("null") )
            OfficeButton.setText(d.office);
        if( !d.site.equals("null") )
            WebSite.setText(d.site);
        if( !d.phone.equals("null") )
            PhoneButton.setText(d.phone);
        if( !d.displayName.equals("null") )
            DisplayName.setText(d.displayName);
        if( d.displayName.equals("null") )
            DisplayName.setText(d.name);
    }

    public void PhoneButtonThread() {
        Thread PhoneCallRunner = new Thread(new Runnable() {
            public void run() {
                String message = "Error: Failed to SetPhoneButton.";
                try {
                    SetPhoneButton();
                } catch (Throwable t) {
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        PhoneCallRunner.start();
    }

    public void SetPhoneButton() {
        PhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivatePhoneCall();
            }
        });
    }

    public void ActivatePhoneCall() {
        String message = "You need to activate Phone permissions for this app";
        try {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                final Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + PhoneButton.getText().toString()));
                DepartmentModelActivity.this.startActivity(callIntent);
            }else{
                String[] CallPermissions = {"android.permission.CALL_PHONE"};
                int requestID = 1;
                requestPermissions(CallPermissions, requestID);
            }
        }catch( Throwable t){
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        String message = "Permission was not granted";
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ActivatePhoneCall();
            } else {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
