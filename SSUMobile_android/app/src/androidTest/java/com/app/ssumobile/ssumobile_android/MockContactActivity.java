package com.app.ssumobile.ssumobile_android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.app.ssumobile.ssumobile_android.models.ContactModel;
import com.app.ssumobile.ssumobile_android.R;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;


/**
 * Created by WestFlow on 10/27/2015.
 */
public class MockContactActivity extends AppCompatActivity {
    TextView Fname;
    TextView Lname;
    TextView Title;
    Button PhoneButton;
    Button EmailButton;
    ContactModel testModel = mock(ContactModel.class);

    Handler handler = new Handler(){
        @Override
        public void handleMessage (Message msg){
            Fname.setText(testModel.getFname());
            Lname.setText(testModel.getLname());
            Title.setText(testModel.getTitle());
            PhoneButton.setText(testModel.getPhone_num());
            EmailButton.setText(testModel.getEmail());
        }
    };

    @Override
    protected void onStart(){
        super.onStart();
         Runnable r = new Runnable() {
            @Override
            public void run() {
                Mockito.when(testModel.getFname()).thenReturn("John");
                Mockito.when(testModel.getLname()).thenReturn("Doe");
                Mockito.when(testModel.getTitle()).thenReturn("Student");
                Mockito.when(testModel.getPhone_num()).thenReturn("310-999-9999");
                Mockito.when(testModel.getEmail()).thenReturn("JohnDoe@gmail.com");
                handler.sendMessage(handler.obtainMessage());
            }
        };// Create a new Thread to handle instantiation of Text values for each TextView and Button
        Thread myThread = new Thread(r);
        myThread.start();
    }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Set the contact_view to the current view
            setContentView(R.layout.contact_view);
            // Set instances of each Button/Text View
            Fname = (TextView) findViewById(R.id.Fname_button);
            Lname = (TextView) findViewById(R.id.Lname_button);
            Title = (TextView) findViewById(R.id.Title_button);
            PhoneButton = (Button) findViewById(R.id.Phone_button);
            EmailButton = (Button) findViewById(R.id.Email_button);

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

