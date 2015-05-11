package com.memoryaid.memoryaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;


public class Homescreen extends ActionBarActivity implements View.OnClickListener {
    public static final String SaveData = "MyPreferenceFiles";
    private String First_Launch;
    private Button btnViewContacts;
    private Button btnManager;
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

        btnManager = (Button) findViewById(R.id.btnManager);
        btnViewContacts = (Button) findViewById(R.id.btnContactView);
        btnSettings = (Button) findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(this);
        btnViewContacts.setOnClickListener(this);
        btnManager.setOnClickListener(this);

        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        First_Launch = settings.getString("First_Launch", "true");

        Print(getApplicationContext().getFilesDir());

        DatabaseHandler db = new DatabaseHandler(this);
        if (First_Launch.equals("true")) {
            CreateNewUser(V);

        } else {
            if (!db.findProfile(settings.getInt("CurrentProfile", 0))) {
                if (db.getAllProfiles().isEmpty()) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Contact_Or_Profile", "Profile");
                    editor.commit();
                    CreateNewUser(V);

                } else {
                    //hier moet code in voor profile list te laden
                }
            }
        }

    }

    public void Print(File path) {
        for (File file : path.listFiles()) {
            Log.e("" + file.isFile(), file.getPath());
            if (file.isDirectory()) Print(file);
        }
    }

    public void CreateNewUser(View view) {
        Intent i = new Intent(this, CreateNewUser.class);
        startActivity(i);
    }

    public void Settings(View view) {
        Intent i = new Intent(this, Settings.class);
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
            case R.id.btnContactView:
                SharedPreferences settings = getSharedPreferences(SaveData, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("Contact_Or_Profile", "Contact");
                editor.putString("ProfileMode", "View");
                editor.commit();
                ContactView(V);
                break;
            case R.id.btnManager:
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
