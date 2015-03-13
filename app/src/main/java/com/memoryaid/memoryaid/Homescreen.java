package com.memoryaid.memoryaid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class Homescreen extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        DatabaseHandler db = new DatabaseHandler(this);

        db.addProfile(new Profile("Jan", "Jansens", "127.0.0.1"));
        db.addContact(new Contact("Jos", "Jansens", "Family", "13.33.33.37", "Jos Jansens is my nephew"));

        if (db.findProfile(1)) {
            db.getProfile().showContacts();
        }
    }

    public void CreateNewUser(View view) {
        Intent i = new Intent(this, CreateNewUser.class);
        startActivity(i);
    }

    public void Settings(View view) {
        //Intent i = new Intent(this,CreateNewUser.class);
        //startActivity(i);
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
