package com.memoryaid.memoryaid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class Homescreen extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        String filesFolder = getApplicationContext().getFilesDir().getAbsolutePath();
        Database parser = new Database("test.xml",filesFolder );
        parser.ResetDatabase();
        parser.AddProfile(new Profile("test","test","test",new ArrayList<Contact>()));
        parser.ShowFile();
        LinkFiles(filesFolder + "/");
    }

    public void LinkFiles(String path) {
        Log.e("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        Log.e("Files", "Size: " + file.length);
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) Log.e("Files", "FileName: " + file[i].getName());
            if (file[i].isDirectory()) LinkFiles(file[i].getAbsolutePath());
        }
    }

    public void CreateNewUser(View view)
    {
        Intent i = new Intent(this,CreateNewUser.class);
        startActivity(i);
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
