package com.memoryaid.memoryaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Homescreen extends ActionBarActivity implements View.OnClickListener {


    public static final String SaveData = "MyPreferenceFiles";
    private String First_Launch;
    private Button btnViewProfiles;
    private Button btnProfileManager;
    private Button btnSettings;

    private View V;

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        btnProfileManager = (Button) findViewById(R.id.btnProfileManager);
        btnViewProfiles = (Button) findViewById(R.id.btnProfileView);
        btnSettings = (Button) findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(this);
        btnViewProfiles.setOnClickListener(this);
        btnProfileManager.setOnClickListener(this);

        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        First_Launch = settings.getString("First_Launch", "true");


        if (First_Launch == "true") {
          Settings(V);
        }


        /*DatabaseHandler db = new DatabaseHandler(this);
        if (!db.findProfile("Jan", "Jansens")) {
            db.addProfile(new Profile("Jan", "Jansens", "2/2/2015", "127.0.0.1"));
            db.addContact(new Contact("Jos1", "Jansens", "2/2/2015", "Family", "13.33.33.37", "Jos1 Jansens is my nephew"));
            db.addContact(new Contact("Jos2", "Jansens", "2/2/2015", "Family", "13.33.33.37", "Jos2 Jansens is my cousin"));
            db.addContact(new Contact("Jos3", "Jansens", "2/2/2015", "Family", "13.33.33.37", "Jos3 Jansens is my wife"));

            db.addProfile(new Profile("BRAK", "OBAMA", "2/2/2015", "127.0.0.1"));
            db.addContact(new Contact("MICHELLE", "OBAMA", "2/2/2015", "Family", "13.33.33.37", "wife"));
            db.addContact(new Contact("SOMETHINGELSE", "OBAMA", "2/2/2015", "Family", "13.33.33.37", "kid"));

        } else {
            ArrayList<Profile> list = db.getAllProfiles();
            for (Profile profile : list) {

                ArrayList<Contact> contactlist = profile.getContacts();

                for (Contact contact : contactlist) {
                    contact.getFullName();
                }

            }
        }*/


    }


    public void Settings(View view) {

        Intent i = new Intent(getApplicationContext(), Settings.class);
        startActivity(i);
    }

    public void ProfileManager(View view) {
        Intent i = new Intent(this, ProfileManager.class);
        startActivity(i);
    }

    public void ContactView(View view) {
        Intent i = new Intent(this, ProfileView.class);
        startActivity(i);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnProfileView:
                ContactView(V);
                break;
            case R.id.btnProfileManager:
                ProfileManager(v);
                break;
            case R.id.btnSettings:
                Settings(v);
                break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homescreen, menu);
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
