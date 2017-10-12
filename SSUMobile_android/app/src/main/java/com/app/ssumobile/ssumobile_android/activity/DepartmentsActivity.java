package com.app.ssumobile.ssumobile_android.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.BuildingModel;
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;
import com.app.ssumobile.ssumobile_android.models.FacStaffModel;
import com.app.ssumobile.ssumobile_android.models.SchoolModel;
import com.app.ssumobile.ssumobile_android.providers.DataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class DepartmentsActivity extends AppCompatActivity {
    ArrayAdapter adapter;
    DataProvider Dal = new DataProvider();

    ArrayList<DepartmentModel> contactsList = new ArrayList<>();
    ArrayList<FacStaffModel> facStaffList = new ArrayList<>();
    ArrayList<BuildingModel> buildingList = new ArrayList<>();
    ArrayList<SchoolModel> schoolList = new ArrayList<>();

    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_view);
        // Set input search bar
        inputSearch = (EditText) findViewById(R.id.input_search);
        
        // Open Keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

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
        adapter.notifyDataSetChanged(); // update cards
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
            for (int i = 0; i < facStaffList.size(); ++i) {
                for (int k = 0; k < contactsList.size(); ++k) {
                    if (contactsList.get(k).id.equals(facStaffList.get(i).department))
                        contactsList.get(k).getFacStaffList().add(facStaffList.get(i));
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

        protected Boolean doInBackground(final String... args) {
            this.dialog.setMessage("Downloading data...");

            Dal.getData(contactsList, facStaffList, buildingList, schoolList);

            Collections.sort(contactsList, new Comparator<DepartmentModel>() {
                @Override
                public int compare(DepartmentModel lhs, DepartmentModel rhs) {
                    return lhs.displayName.compareTo(rhs.displayName);
                }
            });

            return true;
        }
    }
}
