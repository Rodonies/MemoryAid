package com.memoryaid.memoryaid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ProfileView extends ActionBarActivity implements View.OnClickListener{

    public static final String SaveData = "MyPreferenceFiles";
    private int CurrentProfile;
    private String ContactMode;
    private View V;
    private Contact BufferContact;
    private TextView Titel;
    private EditText Name, LastName, Phone, Date_of_Birth, Information, Relation;
    private ImageView imgGallery;
    private ImageView imgCamera;
    private ImageView Photo;
    private static int TAKE_PICTURE = 1;
    private static int SELECT_IMAGE = 2;
    private Dialog dialog;

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
                try{ db.editContact(BufferContact, new Contact(BufferContact.getID(),BufferContact.getCID() ,Name.getText().toString(), LastName.getText().toString(), "Empty", Relation.getText().toString(), Phone.getText().toString(), Information.getText().toString(), db.getProfile().getImagePath()));}
                catch(Exception e)
                {

                }

            }
            Intent i = new Intent(this, ProfileManager.class);
            startActivity(i);
        } else if (ContactMode.equals("Delete")) {
            Intent i = new Intent(this, ProfileManager.class);
            startActivity(i);
        }


    }
    public void onClick(View v) {





        switch (v.getId()) {

            case R.id.ImageLabel: /** AlerDialog when click on Exit */
                SearchPhoto(v);
                break;
            case R.id.PhotoCamera: /** AlerDialog when click on Exit */
                TakePhoto();
                dialog.dismiss();
                break;

            case R.id.PhotoGallery: /** AlerDialog when click on Exit */
                Intent intent = new Intent(Intent.ACTION_PICK);
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                intent.setDataAndType(uri, "image/png");
                startActivityForResult(Intent.createChooser(intent, "Select contact image"), SELECT_IMAGE);
                dialog.dismiss();

                break;

        }


    }
    public void TakePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "temp.png");
        if (photo.exists()) photo.delete();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intent, TAKE_PICTURE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);

        setContentView(R.layout.activity_profile_view2);


        DatabaseHandler db = new DatabaseHandler(this);
        Titel = (TextView) findViewById(R.id.TitleList);
        Titel.setText("Contact List");
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
        if(BufferContact.getNumber().equals("")) Phone.setText("            ");
        else Phone.setText(BufferContact.getNumber());
        Information = (EditText) findViewById(R.id.BasicInfoText);
        if(BufferContact.getInformation().equals("")) Information.setText("            ");
        else Information.setText(BufferContact.getInformation());
        Relation = (EditText) findViewById(R.id.RelationText);
        if(BufferContact.getRelation().equals(""))Relation.setText("            ");
        else Relation.setText(BufferContact.getRelation());
        //Date_of_Birth = (EditText) findViewById(R.id.Date_Of_Birth);
        //Date_of_Birth.setText(BufferContact.getBirthDate());
        Photo = (ImageView) findViewById(R.id.ImageLabel);
        Photo.setOnClickListener(this);
        if(BufferContact.getImageFile()!= null)  Picasso.with(ProfileView.this).load(BufferContact.getImageFile()).resize(Photo.getMaxWidth(),Photo.getMaxHeight()).into(Photo);
        else  Picasso.with(ProfileView.this).load(R.drawable.defaultimage).resize(Photo.getMaxWidth(),Photo.getMaxHeight()).into(Photo);
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
    public void SearchPhoto(View view) {


        dialog = new Dialog(ProfileView.this);
        dialog.setContentView(R.layout.customdialogvenster);
        dialog.setTitle("Select image source");

        imgGallery = (ImageView) dialog.findViewById(R.id.PhotoGallery);
        imgCamera = (ImageView) dialog.findViewById(R.id.PhotoCamera);

        String test = DatabaseHandler.getProfile().getColor();
        if (test != null) {
            switch (DatabaseHandler.getProfile().getColor()) {
                case "Blue":
                    Picasso.with(ProfileView.this).load(R.drawable.galleryblauw).resize(150, 150).into(imgGallery);
                    Picasso.with(ProfileView.this).load(R.drawable.camerablauw).resize(150, 150).into(imgCamera);
                    break;
                case "Red":
                    Picasso.with(ProfileView.this).load(R.drawable.galleryrood).resize(150, 150).into(imgGallery);
                    Picasso.with(ProfileView.this).load(R.drawable.camerarood).resize(150, 150).into(imgCamera);
                    break;
                case "Yellow":
                    Picasso.with(ProfileView.this).load(R.drawable.gallerygeel).resize(150, 150).into(imgGallery);
                    Picasso.with(ProfileView.this).load(R.drawable.camerageel).resize(150, 150).into(imgCamera);
                    break;
                case "Green":
                    Picasso.with(ProfileView.this).load(R.drawable.gallerygroen).resize(150, 150).into(imgGallery);
                    Picasso.with(ProfileView.this).load(R.drawable.cameragroen).resize(150, 150).into(imgCamera);
                    break;
                case "White":
                    Picasso.with(ProfileView.this).load(R.drawable.gallerywit).resize(150, 150).into(imgGallery);
                    Picasso.with(ProfileView.this).load(R.drawable.camerawit).resize(150, 150).into(imgCamera);
                    break;
                case "Black":
                    Picasso.with(ProfileView.this).load(R.drawable.galleryzwart).resize(150, 150).into(imgGallery);
                    Picasso.with(ProfileView.this).load(R.drawable.camerazwart).resize(150, 150).into(imgCamera);
                    break;
                case "Purple":
                    Picasso.with(ProfileView.this).load(R.drawable.gallerypaars).resize(150, 150).into(imgGallery);
                    Picasso.with(ProfileView.this).load(R.drawable.camerapaars).resize(150, 150).into(imgCamera);
                    break;
                case "Pink":
                    Picasso.with(ProfileView.this).load(R.drawable.galleryroos).resize(150, 150).into(imgGallery);
                    Picasso.with(ProfileView.this).load(R.drawable.cameraroos).resize(150, 150).into(imgCamera);
                    break;
                default:
                    Picasso.with(ProfileView.this).load(R.drawable.galleryblauw).resize(150, 150).into(imgGallery);
                    Picasso.with(ProfileView.this).load(R.drawable.camerablauw).resize(150, 150).into(imgCamera);
                    break;
            }
        }


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        dialogButton.setOnClickListener(this);
        imgGallery.setOnClickListener(this);
        imgCamera.setOnClickListener(this);

        dialog.show();


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

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {

        setContentView(R.layout.activity_profile_view);
        Photo = (ImageView) findViewById(R.id.ImageLabel);

        switch (reqCode) {
            case 1:

                if (resCode == Activity.RESULT_OK) {
                    File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "temp.png");

                    if (photo.exists())
                        Picasso.with(getApplicationContext()).load(photo).centerCrop().resize(300, 250).into(Photo);
                    else
                        Picasso.with(getApplicationContext()).load(R.drawable.defaultimage).centerCrop().resize(300, 250).into(Photo);
                    Toast.makeText(this, "photo was added to database", Toast.LENGTH_LONG).show();

                } else if (resCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "no photo was chosen", Toast.LENGTH_LONG).show();
                }

                break;
            case 2:
                try {
                    Picasso.with(getApplicationContext()).load(data.getData()).centerCrop().resize(300, 250).into(Photo);

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

                } catch (Exception e) {
                    Toast.makeText(this, "no photo was selected " + e.getMessage(), Toast.LENGTH_LONG).show();

                }

                break;
            default:
                break;
        }
        Save();
        refresh();
    }

    public void Save()
    {
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "temp.png");
        if (photo.exists()) {
            try {

                File file = new File(DatabaseHandler.getProfile().getContacts().get(BufferContact.getCID()-1).getImagePath());
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
    public void refresh()
    {

        Intent i = new Intent(this,ProfileView.class);
        startActivity(i);
        Toast.makeText(this, "restart app to apply changes", Toast.LENGTH_LONG).show();




    }

}
