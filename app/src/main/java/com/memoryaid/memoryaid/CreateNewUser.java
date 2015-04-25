package com.memoryaid.memoryaid;

import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;

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

import javax.xml.namespace.NamespaceContext;

public class CreateNewUser extends ActionBarActivity implements View.OnClickListener {

    public static final String SaveData = "MyPreferenceFiles";
    private String First_Launch;
    private String Contact_Or_Profile;
    private int CurrentProfile;

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
    private String Extra_Info;
    private String Relation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);

        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        Contact_Or_Profile = settings.getString("Contact_Or_Profile", "Profile");

        if (Contact_Or_Profile == "Profile")
        setContentView(R.layout.activity_create_new_user);
        else
        setContentView(R.layout.activity_contact_adding);

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

        DatabaseHandler db = new DatabaseHandler(this);
        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        Contact_Or_Profile = settings.getString("Contact_Or_Profile", "Profile");
        First_Launch = settings.getString("First_Launch","true");

        if (Contact_Or_Profile == "Profile" || First_Launch == "true") {
            SharedPreferences.Editor editor = settings.edit();
            if (First_Launch == "true")
            {
                editor.putString("First_Launch","false");
                editor.commit();
            }

            Name = ((EditText) findViewById(R.id.First_Name_Field)).getText().toString();
            LastName = ((EditText) findViewById(R.id.Last_Name_Field)).getText().toString();
            Phone = ((EditText) findViewById(R.id.PhoneNumber)).getText().toString();
            BirthDate = ((EditText) findViewById(R.id.Date_Of_Birth)).getText().toString();
            Note = ((EditText) findViewById(R.id.Notes)).getText().toString();
            db.addProfile(new Profile(Name,LastName,Phone,BirthDate,Note));



            if (db.findProfile(Name,LastName))
            {
                editor.putInt("CurrentProfile",db.getProfile().getID());
                editor.commit();
            }
            db.close();





        }
        else if(Contact_Or_Profile == "Contact"){


            Name = ((EditText) findViewById(R.id.Contact_First_Name)).getText().toString();
            LastName = ((EditText) findViewById(R.id.Contact_Last_Name)).getText().toString();
            Phone = ((EditText) findViewById(R.id.Contact_Phone_Number)).getText().toString();
            BirthDate = ((EditText) findViewById(R.id.Contact_Date_Of_Birth)).getText().toString();
            Extra_Info = ((EditText) findViewById(R.id.Contact_Extra_Info)).getText().toString();
            Relation = ((EditText) findViewById(R.id.Contact_Relation)).getText().toString();

            CurrentProfile = settings.getInt("CurrentProfile",1);
            if (db.findProfile(CurrentProfile))
            {
              db.addContact(new Contact(Name,LastName,BirthDate,Relation,Phone,Extra_Info));
              db.close();
            }
        }

        Intent i = new Intent(this, Homescreen.class);
        startActivity(i);
        }

    public void TakePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
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
         db.close();
        return null;
    }


}

