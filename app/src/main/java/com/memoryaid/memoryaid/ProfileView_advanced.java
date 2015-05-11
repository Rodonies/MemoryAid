package com.memoryaid.memoryaid;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ProfileView_advanced extends ActionBarActivity {

    public static final String SaveData = "MyPreferenceFiles";
    private int CurrentProfile;
    private String ContactMode;
    private Profile BufferProfile;
    private TextView Titel;
    private View V;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);
        setContentView(R.layout.activity_profile_view2);
        DatabaseHandler db = new DatabaseHandler(this);
        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        CurrentProfile = settings.getInt("CurrentProfile", 0);
        ContactMode = settings.getString("ProfileMode", "View");
        Titel = (TextView) findViewById(R.id.TitleList);
        Titel.setText("Profile list");
        final ArrayList<Profile> ProfileList = db.getAllProfiles();
        final ListView profileList = (ListView) findViewById(R.id.ListContacts);
        profileList.setAdapter(new AdapterProfiles(this, ProfileList));

        profileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BufferProfile = (Profile) profileList.getItemAtPosition(position);


                if (ContactMode.equals("View")) {
                    //ViewProfile(V, 0);
                } else if (ContactMode.equals("Edit")) {
                    //ViewProfile(V, 1);
                } else if (ContactMode.equals("Delete")) {
                    //AskOption().show();

                }
            }
        });
    }





}
