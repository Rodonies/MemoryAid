package com.memoryaid.memoryaid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class CreateNewUser extends ActionBarActivity {



    private ImageView contactImgView;
    private Button btnAddPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_create_new_user);


        btnAddPhoto = (Button) findViewById(R.id.btnAddPhoto);
        contactImgView = (ImageView) findViewById(R.id.ChosenPhoto);


        contactImgView = (ImageView) findViewById(R.id.ChosenPhoto);


        setContentView(R.layout.activity_create_new_user);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_new_user, menu);
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

    public void onActivityResult(int reqCode, int resCode, Intent data) {


            Toast.makeText(this, String.valueOf(data.getData()), Toast.LENGTH_LONG).show();
            Picasso.with(getApplicationContext()).load(data.getData()).resize(150,150).into(contactImgView);
            btnAddPhoto.setVisibility(View.GONE);



    }


    public void SearchPhoto(View view) {


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Camera/");

        intent.setDataAndType(uri, "image/*");
        startActivityForResult(Intent.createChooser(intent, "Select contact image"),1);





    }


}

