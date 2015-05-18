package com.memoryaid.memoryaid;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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

        if (Contact_Or_Profile.equals("Profile"))
            setContentView(R.layout.activity_create_new_user);
        else
            setContentView(R.layout.activity_contact_adding);

        btnAddPhoto = (Button) findViewById(R.id.btnAddPhoto);
        contactImgView = (ImageView) findViewById(R.id.ChosenPhoto);

    }


    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        switch (reqCode) {
            case 1:

                if (resCode == Activity.RESULT_OK) {
                    File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "temp.png");

                    if (photo.exists())
                        Picasso.with(getApplicationContext()).load(photo).centerCrop().resize(300, 250).into(contactImgView);
                    else
                        Picasso.with(getApplicationContext()).load(R.drawable.defaultimage).centerCrop().resize(300, 250).into(contactImgView);
                    Toast.makeText(this, "photo was added to database", Toast.LENGTH_LONG).show();
                    btnAddPhoto.setVisibility(View.GONE);
                } else if (resCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "no photo was chosen", Toast.LENGTH_LONG).show();
                }

                break;
            case 2:
                try {
                    Picasso.with(getApplicationContext()).load(data.getData()).centerCrop().resize(300, 250).into(contactImgView);

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "temp.png");
                    if (photo.exists()) photo.delete();

                    InputStream in = new FileInputStream(filePath);
                    OutputStream out = new FileOutputStream(photo);

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();


                    Toast.makeText(this, "photo was selected", Toast.LENGTH_LONG).show();
                    btnAddPhoto.setVisibility(View.GONE);
                } catch (Exception e) {
                    Toast.makeText(this, "no photo was selected ", Toast.LENGTH_LONG).show();

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

        String test = DatabaseHandler.getProfile().getColor();
        if (test != null) {
            switch (DatabaseHandler.getProfile().getColor()) {
                case "Blue":
                    Picasso.with(CreateNewUser.this).load(R.drawable.galleryblauw).resize(150, 150).into(imgGallery);
                    Picasso.with(CreateNewUser.this).load(R.drawable.camerablauw).resize(150, 150).into(imgCamera);
                    break;
                case "Red":
                    Picasso.with(CreateNewUser.this).load(R.drawable.galleryrood).resize(150, 150).into(imgGallery);
                    Picasso.with(CreateNewUser.this).load(R.drawable.camerarood).resize(150, 150).into(imgCamera);
                    break;
                case "Yellow":
                    Picasso.with(CreateNewUser.this).load(R.drawable.gallerygeel).resize(150, 150).into(imgGallery);
                    Picasso.with(CreateNewUser.this).load(R.drawable.camerageel).resize(150, 150).into(imgCamera);
                    break;
                case "Green":
                    Picasso.with(CreateNewUser.this).load(R.drawable.gallerygroen).resize(150, 150).into(imgGallery);
                    Picasso.with(CreateNewUser.this).load(R.drawable.cameragroen).resize(150, 150).into(imgCamera);
                    break;
                case "White":
                    Picasso.with(CreateNewUser.this).load(R.drawable.gallerywit).resize(150, 150).into(imgGallery);
                    Picasso.with(CreateNewUser.this).load(R.drawable.camerawit).resize(150, 150).into(imgCamera);
                    break;
                case "Black":
                    Picasso.with(CreateNewUser.this).load(R.drawable.galleryzwart).resize(150, 150).into(imgGallery);
                    Picasso.with(CreateNewUser.this).load(R.drawable.camerazwart).resize(150, 150).into(imgCamera);
                    break;
                case "Purple":
                    Picasso.with(CreateNewUser.this).load(R.drawable.gallerypaars).resize(150, 150).into(imgGallery);
                    Picasso.with(CreateNewUser.this).load(R.drawable.camerapaars).resize(150, 150).into(imgCamera);
                    break;
                case "Pink":
                    Picasso.with(CreateNewUser.this).load(R.drawable.galleryroos).resize(150, 150).into(imgGallery);
                    Picasso.with(CreateNewUser.this).load(R.drawable.cameraroos).resize(150, 150).into(imgCamera);
                    break;
                default:
                    Picasso.with(CreateNewUser.this).load(R.drawable.galleryblauw).resize(150, 150).into(imgGallery);
                    Picasso.with(CreateNewUser.this).load(R.drawable.camerablauw).resize(150, 150).into(imgCamera);
                    break;
            }
        }


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
                Intent intent = new Intent(Intent.ACTION_PICK);
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                intent.setDataAndType(uri, "image/png");

                startActivityForResult(Intent.createChooser(intent, "Select contact image"), SELECT_IMAGE);
                break;
        }
    }

    public void SaveProfile(View view) {

        DatabaseHandler db = new DatabaseHandler(this);
        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        Contact_Or_Profile = settings.getString("Contact_Or_Profile", "Profile");
        First_Launch = settings.getString("First_Launch", "true");
        if (Contact_Or_Profile.equals("Profile") || First_Launch.equals("true")) {

            SharedPreferences.Editor editor = settings.edit();
            Name = ((EditText) findViewById(R.id.First_Name_Field)).getText().toString();
            LastName = ((EditText) findViewById(R.id.Last_Name_Field)).getText().toString();
            Phone = ((EditText) findViewById(R.id.PhoneNumber)).getText().toString();
            BirthDate = ((EditText) findViewById(R.id.Date_Of_Birth)).getText().toString();
            Note = ((EditText) findViewById(R.id.Notes)).getText().toString();

            if (Name.equals("") || LastName.equals("")) {
                Toast.makeText(this, R.string.Error1, Toast.LENGTH_LONG);
            } else {
                db.addProfile(new Profile(Name, LastName, Phone, BirthDate, Note));

                if (db.findProfile(Name, LastName)) {
                    if (First_Launch.equals("true")) {
                        editor.putString("First_Launch", "false");
                        editor.commit();
                    }

                    editor.putInt("CurrentProfile", db.getProfile().getID());
                    editor.commit();
                }
                db.close();

                File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "temp.png");
                if (photo.exists()) {
                    try {
                        File file = new File(DatabaseHandler.getProfile().getImagePath());
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                        InputStream in = new FileInputStream(photo);
                        OutputStream out = new FileOutputStream(file);

                        // Transfer bytes from in to out
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        out.close();

                    } catch (Exception fuck) {
                        Log.e("profilesave", fuck.getMessage());
                    }
                    photo.delete();
                }
                Intent i = new Intent(this, Homescreen.class);
                startActivity(i);
            }


        } else if (Contact_Or_Profile.equals("Contact")) {


            Name = ((EditText) findViewById(R.id.Contact_First_Name)).getText().toString();
            LastName = ((EditText) findViewById(R.id.Contact_Last_Name)).getText().toString();
            Phone = ((EditText) findViewById(R.id.Contact_Phone_Number)).getText().toString();
            BirthDate = ((EditText) findViewById(R.id.Contact_Date_Of_Birth)).getText().toString();
            Extra_Info = ((EditText) findViewById(R.id.Contact_Extra_Info)).getText().toString();
            Relation = ((EditText) findViewById(R.id.Contact_Relation)).getText().toString();

            if (Name.equals("") || LastName.equals("")) {
                Toast.makeText(this, R.string.Error1, Toast.LENGTH_LONG).show();
            } else {
                CurrentProfile = settings.getInt("CurrentProfile", 1);
                if (db.findProfile(CurrentProfile)) {
                    db.addContact(new Contact(Name, LastName, BirthDate, Relation, Phone, Extra_Info));
                    db.findProfile(CurrentProfile);

                    db.close();

                    File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "temp.png");
                    if (photo.exists()) {
                        try {

                            File file = new File(DatabaseHandler.getProfile().getContacts().get(DatabaseHandler.getProfile().getContacts().size() - 1).getImagePath());
                            file.getParentFile().mkdirs();
                            file.createNewFile();
                            InputStream in = new FileInputStream(photo);
                            OutputStream out = new FileOutputStream(file);

                            // Transfer bytes from in to out
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }
                            in.close();
                            out.close();
                        } catch (Exception fuck) {
                            Log.e("contactsave", fuck.getMessage());
                        }
                        photo.delete();
                    }
                }
                Intent i = new Intent(this, Homescreen.class);
                startActivity(i);
            }


        }

    }

    public void TakePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "temp.png");
        if (photo.exists()) photo.delete();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intent, TAKE_PICTURE);
    }


}

