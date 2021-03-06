package com.memoryaid.memoryaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ProfileManager_advanced extends ActionBarActivity implements View.OnClickListener {

    private Button btnAddProfile, btnEditProfile, btnShowProfile, btnDeleteProfile;
    public static final String SaveData = "MyPreferenceFiles";
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this,Homescreen.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);
        setContentView(R.layout.activity_profile_manager_advanced);

        btnAddProfile = (Button) findViewById(R.id.btnAddProfile);
        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        btnShowProfile = (Button) findViewById(R.id.btnChangeProfile);
        btnDeleteProfile = (Button) findViewById(R.id.btnDeleteProfile);

        btnAddProfile.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);
        btnDeleteProfile.setOnClickListener(this);
        btnShowProfile.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAddProfile:
                SharedPreferences settings = getSharedPreferences(SaveData, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("Contact_Or_Profile", "Profile");
                editor.commit();
                CreateNewUser(v);
                break;
            case R.id.btnEditProfile:
                settings = getSharedPreferences(SaveData, 0);
                editor = settings.edit();
                editor.putString("ProfileMode", "Edit");
                editor.commit();
                ProfileView(v);

                break;
            case R.id.btnDeleteProfile:
                settings = getSharedPreferences(SaveData, 0);
                editor = settings.edit();
                editor.putString("ProfileMode", "Delete");
                editor.commit();
                ProfileView(v);
                break;
            case R.id.btnChangeProfile:
                settings = getSharedPreferences(SaveData, 0);
                editor = settings.edit();
                editor.putString("ProfileMode", "Change");
                editor.commit();
                ProfileView(v);
                break;

        }


    }

    public void CreateNewUser(View view) {
        Intent i = new Intent(this, CreateNewUser.class);
        startActivity(i);
    }

    public void ProfileView(View view) {
        Intent i = new Intent(this, ProfileView_advanced.class);
        startActivity(i);
    }

}
