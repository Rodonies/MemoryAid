package com.memoryaid.memoryaid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class ProfileView extends ActionBarActivity {

    public static final String SaveData = "MyPreferenceFiles";
    private int CurrentProfile;
    private String ContactMode;
    private View V;
    private Contact BufferContact;
    private EditText Name,LastName,Phone,Date_of_Birth,Information,Relation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);
        setContentView(R.layout.activity_profile_view2);

        DatabaseHandler db = new DatabaseHandler(this);
        SharedPreferences settings = getSharedPreferences(SaveData,0);
        CurrentProfile = settings.getInt("CurrentProfile",1);
        ContactMode = settings.getString("ProfileMode","View");

        if (db.findProfile(CurrentProfile)) {

                ArrayList<Contact> contactlist = db.getProfile().getContacts();
                final ListView ContactList = (ListView) findViewById(R.id.ListContacts);
                 ContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BufferContact = (Contact) ContactList.getItemAtPosition(position);

                    if (ContactMode.equals("View"))
                    {
                        ViewProfile(V);
                    }
                    else if(ContactMode.equals("Edit"))
                    {

                    }
                    else if(ContactMode.equals("Delete"))
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

        Name = (EditText) findViewById(R.id.FirstNameText);
        Name.setText(BufferContact.getFirstName());
        LastName = (EditText) findViewById(R.id.LastNameText);
        LastName.setText(BufferContact.getLastName());
        Phone = (EditText) findViewById(R.id.PhonenmrText);
        Phone.setText(BufferContact.getFirstName());
        Information = (EditText) findViewById(R.id.BasicInfoText);
        Information.setText(BufferContact.getInformation());
        Relation = (EditText) findViewById(R.id.RelationText);
        Relation.setText(BufferContact.getRelation());


    }





}
