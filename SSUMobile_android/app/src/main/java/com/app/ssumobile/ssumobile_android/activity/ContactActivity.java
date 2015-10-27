package com.app.ssumobile.ssumobile_android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.app.ssumobile.ssumobile_android.models.ContactModel;
import com.app.ssumobile.ssumobile_android.R;

/**
 * Created by WestFlow on 10/22/2015.
 */

public class ContactActivity extends AppCompatActivity {
    private TextView Fname;
    private TextView Lname;
    private TextView Title;
    private Button PhoneButton;
    private Button EmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set instances of each Button/Text View
        Fname = (TextView)findViewById(R.id.Fname_button);
        Lname = (TextView)findViewById(R.id.Lname_button);
        Title = (TextView)findViewById(R.id.Title_button);
        PhoneButton = (Button)findViewById(R.id.Phone_button);
        EmailButton = (Button)findViewById(R.id.Email_button);


        Runnable r = new Runnable() {
            @Override
            public void run() {
                MockContactProvider();
            }
        };
        Thread myThread = new Thread(r);
        myThread.start();


        setContentView(R.layout.contact_view);

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

    public void MockContactProvider(){

    }
}
