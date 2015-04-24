package com.memoryaid.memoryaid;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.jar.Attributes;

public class CreateNewUser extends ActionBarActivity implements View.OnClickListener {

    public static final String PREFS_FIRST_LAUNCH = "MyPreferenceFiles";
    private String First_Launch;

    private static int TAKE_PICTURE = 1;
    private static int SELECT_IMAGE = 2;
    private Dialog dialog;
    private ImageView contactImgView;
    private ImageView imgGallery;
    private ImageView imgCamera;
    private Button btnAddPhoto;
    private int CURRENT_PHOTONUMBER;

    private Uri imageUri;


    private String Name;
    private String LastName;
    private String Phone;
    private String BirthDate;
    private String Note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);
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
        switch (reqCode) {
            case 1:

                if (resCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    Picasso.with(getApplicationContext()).load(selectedImage).centerCrop().resize(300, 250).into(contactImgView);
                    Toast.makeText(this, "photo was added to database", Toast.LENGTH_LONG).show();
                    CURRENT_PHOTONUMBER++;
                    btnAddPhoto.setVisibility(View.GONE);
                } else if (resCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "no photo was chosen", Toast.LENGTH_LONG).show();
                }

                break;
            case 2:
                try {
                    Picasso.with(getApplicationContext()).load(data.getData()).centerCrop().resize(300, 250).into(contactImgView);
                    Toast.makeText(this, "photo was selected", Toast.LENGTH_LONG).show();
                    btnAddPhoto.setVisibility(View.GONE);
                } catch (Exception e) {
                    Toast.makeText(this, "no photo was selected", Toast.LENGTH_LONG).show();
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

        Picasso.with(CreateNewUser.this).load(R.drawable.galleryblauw).resize(150, 150).into(imgGallery);
        Picasso.with(CreateNewUser.this).load(R.drawable.camerablauw).resize(150, 150).into(imgCamera);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        dialogButton.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        imgCamera.setOnClickListener(this);

        dialog.show();


    }


    public void CloseDialog() {
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        CloseDialog();

        switch (v.getId()) {

            case R.id.PhotoCamera: /** AlerDialog when click on Exit */
                TakePhoto();
                break;
            case R.id.PhotoGallery: /** AlerDialog when click on Exit */
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Camera/");
                intent.setDataAndType(uri, "image/*");
                startActivityForResult(Intent.createChooser(intent, "Select contact image"), SELECT_IMAGE);
                break;
        }
    }

    public void SaveProfile(View view) {
        Name = ((EditText) findViewById(R.id.First_Name_Field)).toString();
        LastName = ((EditText) findViewById(R.id.Last_Name_Field)).toString();
        Phone = ((EditText) findViewById(R.id.PhoneNumber)).toString();
        BirthDate = ((EditText) findViewById(R.id.Date_Of_Birth)).toString();
        Note = ((EditText) findViewById(R.id.Notes)).toString();
        DatabaseHandler db = new DatabaseHandler(this);
        db.addProfile(new Profile(Name, LastName, BirthDate, Phone, Note));
        db.close();
        Intent i = new Intent(this, Homescreen.class);
        startActivity(i);
        }

    public void TakePhoto() {

        String FirstName = ((EditText) findViewById(R.id.First_Name_Field)).getText().toString();

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //test.png -> naam gebruiker + datum + tijd ofzo voor uniek te maken
        File photo = new File(GetPath());
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public String GetPath() {
        DatabaseHandler db = new DatabaseHandler(this);
        if (db.findProfile(Name, LastName)) {
            return db.getProfile().getImage();
        }
        return null;
    }


}

