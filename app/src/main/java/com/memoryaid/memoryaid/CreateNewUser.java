package com.memoryaid.memoryaid;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

public class CreateNewUser extends ActionBarActivity implements View.OnClickListener {


    private Dialog dialog;

    private ImageView contactImgView;
    private ImageView imgGallery;
    private ImageView imgCamera;

    private Button btnAddPhoto;

    private static int TAKE_PICTURE = 1;
    private static int SELECT_IMAGE = 2;
    private int CURRENT_PHOTONUMBER;

    private Uri imageUri;


    private String CURRENT_PHOTO ;

    private String FirstName;

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

    @Override
     protected void onActivityResult(int reqCode, int resCode, Intent data) {
          switch(reqCode) {
            case 1:
                Uri selectedImage = imageUri;
                Picasso.with(getApplicationContext()).load(selectedImage).centerCrop().resize(300, 250).into(contactImgView);
                Toast.makeText(this,"photo was added to database",Toast.LENGTH_LONG).show();
                CURRENT_PHOTONUMBER++;
                btnAddPhoto.setVisibility(View.GONE);
                break;
            case 2:
                try {
                    Picasso.with(getApplicationContext()).load(data.getData()).centerCrop().resize(300, 250).into(contactImgView);
                    Toast.makeText(this,"photo was selected",Toast.LENGTH_LONG).show();
                    btnAddPhoto.setVisibility(View.GONE);
                }catch (Exception e){
                    Toast.makeText(this,"no photo was selected",Toast.LENGTH_LONG).show();
                }

                break;
            default:
                break;
        }


    }


    public void SearchPhoto(View view) {


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

            case R.id.PhotoCamera: /** AlerDialog when click on Exit */
                 TakePhoto();
                break;

            case R.id.PhotoGallery: /** AlerDialog when click on Exit */

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Camera/");
                intent.setDataAndType(uri, "image/*");
                startActivityForResult(Intent.createChooser(intent, "Select contact image"),SELECT_IMAGE);

                break;

        }

    }
    public void TakePhoto(){

        String FirstName = ((EditText) findViewById(R.id.First_Name_Field)).getText().toString();

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //test.png -> naam gebruiker + datum + tijd ofzo voor uniek te maken
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),FirstName + CURRENT_PHOTONUMBER + ".png");
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent,TAKE_PICTURE);
    }





}

