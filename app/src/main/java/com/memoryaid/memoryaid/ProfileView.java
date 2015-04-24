package com.memoryaid.memoryaid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class ProfileView extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);
        setContentView(R.layout.activity_profile_view2);

        DatabaseHandler db = new DatabaseHandler(this);

        if (db.findProfile(1)) {
            db.addContact(new Contact("Jos1", "Jansens", "2/2/2015", "Family", "13.33.33.37", "Jos1 Jansens is my nephew"));

            ArrayList<Profile> list = db.getAllProfiles();

            for (Profile profile : list) {

                ArrayList<Contact> contactlist = profile.getContacts();
                ListView ContactList = (ListView) findViewById(R.id.ListContacts);
                ContactList.setAdapter(new AdapterContacts(this, contactlist));

                //ArrayList<String> ContactNames = new ArrayList<String>();
                /*
                for (Contact contact : contactlist) {
                    ContactNames.add(contact.getFirstName()+ contact.getLastName());
                }
                */


            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_view, menu);
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
