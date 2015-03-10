package com.memoryaid.memoryaid;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class CreateNewUser extends ActionBarActivity implements View.OnClickListener {


    private Dialog dialog;
    private ImageView contactImgView;
    private ImageView imgGallery;
    private ImageView imgCamera;
    private Button btnAddPhoto;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_create_new_user);
        btnAddPhoto = (Button) findViewById(R.id.btnAddPhoto);
        contactImgView = (ImageView) findViewById(R.id.ChosenPhoto);


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




        // custom dialog

        dialog = new Dialog(CreateNewUser.this);
        dialog.setContentView(R.layout.customdialogvenster);
        dialog.setTitle("Select image source");

        imgGallery = (ImageView) dialog.findViewById(R.id.PhotoGallery);
        imgCamera = (ImageView) dialog.findViewById(R.id.PhotoCamera);

        Picasso.with(CreateNewUser.this).load(R.drawable.galleryblue).resize(150, 150).into(imgGallery);
        Picasso.with(CreateNewUser.this).load(R.drawable.camerablue).resize(150,150).into(imgCamera);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        dialogButton.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        imgCamera.setOnClickListener(this);

        dialog.show();


    }


    public void CloseDialog()
    {
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        CloseDialog();

        switch(v.getId()){

            case R.id.dialogButtonOK: /** Start a new Activity MyCards.java */


                break;

            case R.id.PhotoCamera: /** AlerDialog when click on Exit */

                break;

            case R.id.PhotoGallery: /** AlerDialog when click on Exit */

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Camera/");
                intent.setDataAndType(uri, "image/*");
                startActivityForResult(Intent.createChooser(intent, "Select contact image"),1);

                break;
        }

    }



}

