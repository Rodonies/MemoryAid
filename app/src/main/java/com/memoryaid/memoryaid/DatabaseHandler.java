package com.memoryaid.memoryaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/*
Werking van databasehandler:

DatabaseHandler db = new DatabaseHandler(this); //Nieuwe datababasehandler aanmaken
if (db.findProfile("naam", "achternaam")) //Profiel zoeken met deze naam/achternaam,
                                          //je kan ook db.findProfile(id) om een profiel te zoeken op id numers
                                          //Geeft true terug als het profiel bestaat, false als het niet bestaat
{
    //profiel bestaat, je kan deze vinden in db.getProfile(), je kan de data NIET AANPASSEN via deze methode
    //data aanpassen gebeurt via db.editProfile(nieuwe data)
}
else
{
    //Profiel niet gevonden
}


//volgende code is een voorbeeld voor de achternaam van het tweede profiel te veranderen naar "Jansens"

DatabaseHandler db = new DatabaseHandler(this);
if (db.findProfile(2))
{
    db.editProfile(null, "Jansens", null);
}

//volgende code is een voorbeeld om de voornaam van het profiel met de naam "Jos Joskens"
//die geen contacten heeft te veranderen naar "Jef" en daarnaa een contact met de naam "Jos Joskens" toe te voegen

DatabaseHandler db = new DatabaseHandler(this);
if (db.findProfile("Jos", "Joskens"))
{
    db.getProfile(); //Geeft profiel met naam "Jos Joskens" zonder contacten
    db.editProfile("Jef", null , null);
    db.addContact(new Contact("Jos", "Joskens", "relatie", "nummer", "informatie");
    db.getProfile(); //Geeft profiel met naam "Jef Joskens" met 1 contact, namelijk "Jos Joskens"
}


//volgende code verwijdert het eerste profiel, inclusief contacten en fotos

DatabaseHandler db = new DatabaseHandler(this);
if (findprofile(1)) db.deleteProfile();

*/

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NAME = "database";

    private static final String TABLE_PROFILES = "profiles";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_SETTINGS = "settings";

    private static final String KEY_ID = "id";
    private static final String KEY_PROFILEID = "profileid";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_IMAGEPATH = "imagepath";

    private static final String KEY_RELATION = "relation";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_INFORMATION = "information";

    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_SIZE = "size";
    private static final String KEY_COLOR = "color";

    private static Profile _profile;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILES_TABLE =
                "CREATE TABLE `" + TABLE_PROFILES + "` (\n" +
                        "\t`" + KEY_ID + "`\tINTEGER NOT NULL PRIMARY KEY,\n" +
                        "\t`" + KEY_FIRSTNAME + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_LASTNAME + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_NUMBER + "`\tTEXT NOT NULL,\n" +
                        ");";

        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE `" + TABLE_CONTACTS + "` (\n" +
                        "\t`" + KEY_ID + "`\tINTEGER NOT NULL PRIMARY KEY,\n" +
                        "\t`" + KEY_PROFILEID + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_FIRSTNAME + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_LASTNAME + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_RELATION + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_NUMBER + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_INFORMATION + "`\tTEXT NOT NULL,\n" +
                        ");";

        String CREATE_SETTINGS_TABLE =
                "CREATE TABLE `" + TABLE_SETTINGS + "` (\n" +
                        "\t`" + KEY_ID + "`\tINTEGER NOT NULL,\n" +
                        "\t`" + KEY_LANGUAGE + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_SIZE + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_COLOR + "`\tTEXT NOT NULL\n" +
                        ");";

        db.execSQL(CREATE_PROFILES_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_SETTINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("drop", "DROPPED");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);

        onCreate(db);
    }


    void addProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, profile.getFirstName());
        values.put(KEY_LASTNAME, profile.getLastName());
        values.put(KEY_NUMBER, profile.getNumber());
        values.put(KEY_IMAGEPATH, profile.getImagePath());

        db.insert(TABLE_PROFILES, null, values);
        db.close();

        findProfile(profile.getFirstName(), profile.getLastName());
    }

    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PROFILEID, _profile.getID());
        values.put(KEY_FIRSTNAME, contact.getFirstName());
        values.put(KEY_LASTNAME, contact.getLastName());
        values.put(KEY_RELATION, contact.getRelation());
        values.put(KEY_NUMBER, contact.getNumber());
        values.put(KEY_INFORMATION, contact.getInformation());
        String path = _profile.getImagePath() + "/" + contact.getID() + "_" + contact.getFirstName() + "_" + contact.getLastName();
        values.put(KEY_IMAGEPATH, path);

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }


    boolean findProfile(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFILES, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_NUMBER}, KEY_ID + " = ?", new String[]{Integer.toString(id)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            _profile = new Profile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            db.close();
            updateContacts();
            return true;
        }
        db.close();
        return false;
    }

    boolean findProfile(String firstname, String lastname) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFILES, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_NUMBER}, KEY_FIRSTNAME + " = ? AND " + KEY_LASTNAME + " = ?", new String[]{firstname, lastname}, null, null, null, null);
        if (cursor.moveToFirst()) {
            _profile = new Profile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            db.close();
            updateContacts();
            return true;
        }
        db.close();
        return false;
    }

    Profile getProfile() {
        return _profile;
    }

    void updateContacts() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_RELATION, KEY_NUMBER, KEY_INFORMATION}, KEY_PROFILEID + " = ?", new String[]{Integer.toString(_profile.getID())}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                _profile.addContact(new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), _profile.getImagePath()));
            } while (cursor.moveToNext());
        }
        db.close();
    }

    public boolean editProfile(String newfirstname, String newlastname, String newnumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (newfirstname == null) newfirstname = _profile.getFirstName();
        if (newlastname == null) newfirstname = _profile.getFirstName();
        if (newnumber == null) newfirstname = _profile.getFirstName();
        String oldpath = _profile.getImagePath();
        _profile.updateImagePath(_profile.getID() + "_" + _profile.getFirstName() + "_" + _profile.getLastName());

        new File(oldpath).renameTo(new File(_profile.getImagePath()));

        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, newfirstname);
        values.put(KEY_LASTNAME, newlastname);
        values.put(KEY_NUMBER, newnumber);
        values.put(KEY_IMAGEPATH, _profile.getImagePath());

        if (db.update(TABLE_PROFILES, values, KEY_ID + " = ?", new String[]{String.valueOf(_profile.getID())}) == 1) return true;
        else return false;
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] { String.valueOf(contact.getID()) });
        new File(contact.getImagePath()).delete();
        db.close();
    }

    // Deleting single contact
    public void deleteProfile() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PROFILES, KEY_ID + " = ?", new String[] { String.valueOf(_profile.getID()) });
        db.delete(TABLE_CONTACTS, KEY_PROFILEID + " = ?", new String[] { String.valueOf(_profile.getID()) });
        new File(_profile.getImagePath()).delete();
        _profile = null;
        db.close();
    }
}