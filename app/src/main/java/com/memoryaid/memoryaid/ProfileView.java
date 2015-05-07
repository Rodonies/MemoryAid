package com.memoryaid.memoryaid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    private EditText Name, LastName, Phone, Date_of_Birth, Information, Relation;

    @Override
    public void onBackPressed() {
        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        ContactMode = settings.getString("ProfileMode", "View");
        if (ContactMode.equals("View")) {
            Intent i = new Intent(this, Homescreen.class);
            startActivity(i);
        } else if (ContactMode.equals("Edit")) {
            DatabaseHandler db = new DatabaseHandler(this);
            settings = getSharedPreferences(SaveData, 0);
            CurrentProfile = settings.getInt("CurrentProfile", 0);
            if (db.findProfile(CurrentProfile)) {

            }
            Intent i = new Intent(this, ProfileManager.class);
            startActivity(i);
        } else if (ContactMode.equals("Delete")) {
            Intent i = new Intent(this, ProfileManager.class);
            startActivity(i);
        }


    }

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
        if (db.findProfile(CurrentProfile)) {

            final ArrayList<Contact> contactlist = db.getProfile().getContacts();
            final ListView ContactList = (ListView) findViewById(R.id.ListContacts);
            ContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    BufferContact = (Contact) ContactList.getItemAtPosition(position);

                    if (ContactMode.equals("View")) {
                        ViewProfile(V, 0);
                    } else if (ContactMode.equals("Edit")) {
                        ViewProfile(V, 1);
                    } else if (ContactMode.equals("Delete")) {
                        AskOption().show();

                    }
                }
            });
            ContactList.setAdapter(new AdapterContacts(this, contactlist));
            db.close();

        }


    }

    void ViewProfile(View v, int mode) {
        setContentView(R.layout.activity_profile_view);

        Name = (EditText) findViewById(R.id.FirstNameText);
        Name.setText(BufferContact.getFirstName());
        LastName = (EditText) findViewById(R.id.LastNameText);
        LastName.setText(BufferContact.getLastName());
        Phone = (EditText) findViewById(R.id.PhonenmrText);
        Phone.setText(BufferContact.getNumber());
        Information = (EditText) findViewById(R.id.BasicInfoText);
        Information.setText(BufferContact.getInformation());
        Relation = (EditText) findViewById(R.id.RelationText);
        Relation.setText(BufferContact.getRelation());

        switch (mode) {
            case 0:
                Name.setClickable(false);
                Name.setFocusable(false);
                LastName.setClickable(false);
                LastName.setFocusable(false);
                Phone.setClickable(false);
                Phone.setFocusable(false);
                Information.setClickable(false);
                Information.setFocusable(false);
                Relation.setClickable(false);
                Relation.setFocusable(false);


                break;
            case 1:
                Name.setClickable(true);
                Name.setFocusable(true);
                LastName.setClickable(true);
                LastName.setFocusable(true);
                Phone.setClickable(true);
                Phone.setFocusable(true);
                Information.setClickable(true);
                Information.setFocusable(true);
                Relation.setClickable(true);
                Relation.setFocusable(true);
                break;
            default:
                break;

        }


    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        db.deleteContact(BufferContact);
                        dialog.dismiss();
                        Intent i = new Intent(getApplicationContext(), ProfileManager.class);
                        startActivity(i);
                    }

                })


                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }


}
