package com.memoryaid.memoryaid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ProfileView extends ActionBarActivity {

    public static final String SaveData = "MyPreferenceFiles";
    private int CurrentProfile;
    private String ContactMode;

    private String Name,LastName,Phone,Date_of_Birth,Information,Relation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);
        setContentView(R.layout.activity_profile_view2);

        DatabaseHandler db = new DatabaseHandler(this);
        SharedPreferences settings = getSharedPreferences(SaveData,0);
        CurrentProfile = settings.getInt("CurrentProfile",1);
        ContactMode = settings.getString("ProfileMode","view");

        if (db.findProfile(CurrentProfile)) {

                ArrayList<Contact> contactlist = db.getProfile().getContacts();
                ListView ContactList = (ListView) findViewById(R.id.ListContacts);
                ContactList.setOnClickListener(new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContactMode == "View")
                        {

                        }
                        else if(ContactMode == "Edit")
                        {

                        }
                        else if(ContactMode == "Delete")
                        {

                        }
                    }
                });
                ContactList.setAdapter(new AdapterContacts(this, contactlist));
                db.close();

        }
        /*
        else{
            ArrayList<Profile> list = db.getAllProfiles();}*/



    }
    void ViewProfile(View v)
    {
        setContentView(R.layout.activity_profile_view);
         Name = ((EditText) findViewById(R.id.FirstNameText)).getText().toString();

    }





}
