package com.memoryaid.memoryaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ProfileManager extends FragmentActivity implements View.OnClickListener {

    private Button btnAddContact;
    private Button btnEditContact;
    private Button btnDeleteContact;
    private Button btnProfileManager;

    public static boolean test = false;
    public static final String SaveData = "MyPreferenceFiles";

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, Homescreen.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);

        setContentView(R.layout.activity_profile_manager);

        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnEditContact = (Button) findViewById(R.id.btnEditContact);
        btnDeleteContact = (Button) findViewById(R.id.btnDeleteContact);
        btnProfileManager = (Button) findViewById(R.id.btnProfileManager);

        btnAddContact.setOnClickListener(this);
        btnEditContact.setOnClickListener(this);
        btnDeleteContact.setOnClickListener(this);
        btnProfileManager.setOnClickListener(this);

        btnProfileManager.setEnabled(test);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAddContact:
                SharedPreferences settings = getSharedPreferences(SaveData, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("Contact_Or_Profile", "Contact");
                editor.putString("ProfileMode", "View");
                editor.commit();
                CreateNewUser(v);
                break;
            case R.id.btnEditContact:

                settings = getSharedPreferences(SaveData, 0);
                editor = settings.edit();
                editor.putString("ProfileMode", "Edit");
                editor.commit();
                ContactView(v);

                break;
            case R.id.btnDeleteContact:
                settings = getSharedPreferences(SaveData, 0);
                editor = settings.edit();
                editor.putString("ProfileMode", "Delete");
                editor.commit();
                ContactView(v);
                break;
            case R.id.btnProfileManager:
                AdvancedProfileManager(v);
                break;

        }

    }

    public void CreateNewUser(View view) {
        Intent i = new Intent(this, CreateNewUser.class);
        startActivity(i);
    }

    public void AdvancedProfileManager(View view) {
        Intent i = new Intent(this, ProfileManager_advanced.class);
        startActivity(i);
    }

    public void ContactView(View view) {
        Intent i = new Intent(this, ProfileView.class);
        startActivity(i);
    }


}
