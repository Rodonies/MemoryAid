package com.memoryaid.memoryaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/*
Werking van databasehandler:


//Nieuwe datababasehandler aanmaken
//Profiel zoeken met deze naam/achternaam,
//je kan ook db.findProfile(id) om een profiel te zoeken op id numers
//Geeft true terug als het profiel bestaat, false als het niet bestaat
DatabaseHandler db = new DatabaseHandler(this);
if (db.findProfile("naam", "achternaam"))
{
    //profiel bestaat, je kan deze vinden in db.getProfile(), je kan de data NIET AANPASSEN via deze methode
    //data aanpassen gebeurt via db.editProfile(nieuwe data)
}
else
{
    //Profiel niet gevonden dus aanmaken
    db.addProfile(new Profile("naam", "achternaam", "geboortedatum", "nummer", "note"));
}


//Volgende code is voor een profiel te editen
DatabaseHandler db = new DatabaseHandler(this);
Profile newprofile = new Profile("naam", "achternaam", "geboortedatum", "nummer", "note");
db.editProfile(newprofile);


//Volgende code is voor contacten te editen
DatabaseHandler db = new DatabaseHandler(this);
Contact newcontact = new Contact("Jos", "Joskens", "relatie", "nummer", "informatie");
db.editContact(oldcontact, newcontact);


//volgende code verwijdert het profiel, inclusief contacten en fotos
DatabaseHandler db = new DatabaseHandler(this);
db.deleteProfile();


//Volgende code voegt settings toe aan het profiel
db.saveSettings("Big", "Rood");
//Volgende code zet alleen de grootte op Big op het profiel en laat de kleur onverandert
db.saveSettings("Big", null);
*/

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 33;
    private static final String DATABASE_NAME = "database";

    private static final String TABLE_PROFILES = "profiles";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_SETTINGS = "settings";

    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "birthdate";
    private static final String KEY_PROFILEID = "profileid";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";

    private static final String KEY_RELATION = "relation";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_INFORMATION = "information";

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
                        "\t`" + KEY_DATE + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_NUMBER + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_INFORMATION + "`\tTEXT NOT NULL\n" +
                        ");";

        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE `" + TABLE_CONTACTS + "` (\n" +
                        "\t`" + KEY_ID + "`\tINTEGER NOT NULL PRIMARY KEY,\n" +
                        "\t`" + KEY_PROFILEID + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_FIRSTNAME + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_LASTNAME + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_DATE + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_RELATION + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_NUMBER + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_INFORMATION + "`\tTEXT NOT NULL\n" +
                        ");";

        String CREATE_SETTINGS_TABLE =
                "CREATE TABLE `" + TABLE_SETTINGS + "` (\n" +
                        "\t`" + KEY_ID + "`\tINTEGER NOT NULL,\n" +
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


    public boolean addProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_FIRSTNAME, profile.getFirstName());
            values.put(KEY_LASTNAME, profile.getLastName());
            values.put(KEY_DATE, profile.getBirthDate());
            values.put(KEY_NUMBER, profile.getNumber());
            values.put(KEY_INFORMATION, profile.getInformation());

            db.insert(TABLE_PROFILES, null, values);
            db.close();

            if (findProfile(profile.getFirstName(), profile.getLastName())) {
                saveSettings("Medium", "Blue");
                loadSettings();
                return true;
            } else return false;
        } catch (Exception e) {
            Log.e("addProfile", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PROFILEID, _profile.getID());
        values.put(KEY_FIRSTNAME, contact.getFirstName());
        values.put(KEY_LASTNAME, contact.getLastName());
        values.put(KEY_DATE, contact.getBirthDate());
        values.put(KEY_RELATION, contact.getRelation());
        values.put(KEY_NUMBER, contact.getNumber());
        values.put(KEY_INFORMATION, contact.getInformation());

        String path = _profile.getImagePath() + "/" + contact.getID() + "_" + contact.getFirstName() + "_" + contact.getLastName() + "/";
        new File(path).mkdirs();

        try {
            db.insert(TABLE_CONTACTS, null, values);
            return true;
        } catch (Exception e) {
            Log.e("addContact", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean findProfile(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.query(TABLE_PROFILES, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_DATE, KEY_NUMBER, KEY_INFORMATION}, KEY_ID + " = ?", new String[]{Integer.toString(id)}, null, null, null, null);

            if (cursor.moveToFirst()) {
                _profile = new Profile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                updateContacts();
                loadSettings();
                initializeProfile();
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e("findProfile", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean findProfile(String firstname, String lastname) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.query(TABLE_PROFILES, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_DATE, KEY_NUMBER, KEY_INFORMATION}, KEY_FIRSTNAME + " = ? AND " + KEY_LASTNAME + " = ?", new String[]{firstname, lastname}, null, null, null, null);

            if (cursor.moveToFirst()) {
                _profile = new Profile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                updateContacts();
                loadSettings();
                initializeProfile();
                return true;
            }
            db.close();
            return false;
        } catch (Exception e) {
            Log.e("findProfile", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public static Profile getProfile() {
        if (_profile == null) {
            Profile prof = new Profile(null, null, null, null, null, null);
            prof.updateSettings("Medium", "Blue");
            return prof;
        } else return _profile;
    }

    public boolean editProfile(Profile oldprofile, Profile newprofile) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (!oldprofile.getImagePath().equals(newprofile.getImagePath())) Copy(oldprofile.getImagePath(), newprofile.getImagePath(), true);

        ContentValues values = new ContentValues();

        values.put(KEY_FIRSTNAME, newprofile.getFirstName());
        values.put(KEY_LASTNAME, newprofile.getLastName());
        values.put(KEY_DATE, newprofile.getBirthDate());
        values.put(KEY_NUMBER, newprofile.getNumber());
        values.put(KEY_INFORMATION, newprofile.getInformation());

        try {
            db.update(TABLE_PROFILES, values, KEY_ID + " = ?", new String[]{String.valueOf(oldprofile.getID())});
            findProfile(newprofile.getID());
            return true;
        } catch (Exception e) {
            Log.e("editProfile", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean editContact(Contact oldcontact, Contact newcontact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        if (!oldcontact.getImagePath().equals(newcontact.getImagePath())) Copy(oldcontact.getImagePath(), newcontact.getImagePath(), true);

        values.put(KEY_FIRSTNAME, newcontact.getFirstName());
        values.put(KEY_LASTNAME, newcontact.getLastName());
        values.put(KEY_DATE, newcontact.getBirthDate());
        values.put(KEY_RELATION, newcontact.getRelation());
        values.put(KEY_NUMBER, newcontact.getNumber());
        values.put(KEY_INFORMATION, newcontact.getInformation());

        try {
            db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[]{String.valueOf(oldcontact.getID())});
            updateContacts();
            return true;
        } catch (Exception e) {
            Log.e("editContact", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean saveSettings(String size, String color) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (size == null) size = _profile.getSize();
        if (color == null) color = _profile.getColor();

        ContentValues values = new ContentValues();

        values.put(KEY_ID, _profile.getID());
        values.put(KEY_SIZE, size);
        values.put(KEY_COLOR, color);

        try {
            Cursor cursor = db.query(TABLE_SETTINGS, new String[]{KEY_SIZE, KEY_COLOR}, KEY_ID + " = ?", new String[]{Integer.toString(_profile.getID())}, null, null, null, null);

            if (cursor.moveToFirst()) db.update(TABLE_SETTINGS, values, KEY_ID + " = ?", new String[]{String.valueOf(_profile.getID())});
            else db.insert(TABLE_SETTINGS, null, values);

            loadSettings();
            return true;
        } catch (Exception e) {
            Log.e("saveSettings", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[]{String.valueOf(contact.getID())});
            new File(contact.getImagePath()).getParentFile().delete();
            updateContacts();
            return true;
        } catch (Exception e) {
            Log.e("deleteContact", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean deleteProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(TABLE_PROFILES, KEY_ID + " = ?", new String[]{String.valueOf(profile.getID())});
            db.delete(TABLE_CONTACTS, KEY_PROFILEID + " = ?", new String[]{String.valueOf(profile.getID())});
            db.delete(TABLE_SETTINGS, KEY_ID + " = ?", new String[]{String.valueOf(profile.getID())});
            new File(profile.getImagePath()).getParentFile().delete();
            return true;
        } catch (Exception e) {
            Log.e("deleteProfile", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    private boolean updateContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        _profile.getContacts().clear();

        try {
            Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_DATE, KEY_RELATION, KEY_NUMBER, KEY_INFORMATION}, KEY_PROFILEID + " = ?", new String[]{Integer.toString(_profile.getID())}, null, null, null, null);

            int contactID = 1;
            if (cursor.moveToFirst()) {
                do {
                    _profile.addContact(new Contact(cursor.getInt(0), contactID, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), _profile.getImagePath()));
                    contactID++;
                } while (cursor.moveToNext());
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e("updateContacts", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    private boolean loadSettings() {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(TABLE_SETTINGS, new String[]{KEY_SIZE, KEY_COLOR}, KEY_ID + " = ?", new String[]{Integer.toString(_profile.getID())}, null, null, null, null);

            if (cursor.moveToFirst()) {
                _profile.updateSettings(cursor.getString(0), cursor.getString(1));
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e("loadSettings", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public ArrayList<Profile> getAllProfiles() {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            ArrayList<Profile> list = new ArrayList<Profile>();
            Cursor cursor = db.query(TABLE_PROFILES, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_DATE, KEY_NUMBER, KEY_INFORMATION}, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Profile newprofile = new Profile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

                    Cursor cursor2 = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_DATE, KEY_RELATION, KEY_NUMBER, KEY_INFORMATION}, KEY_PROFILEID + " = ?", new String[]{Integer.toString(newprofile.getID())}, null, null, null, null);
                    int contactID = 1;
                    if (cursor2.moveToFirst()) {
                        do {
                            newprofile.addContact(new Contact(cursor2.getInt(0), contactID, cursor2.getString(1), cursor2.getString(2), cursor2.getString(3), cursor2.getString(4), cursor2.getString(5), cursor2.getString(6), newprofile.getImagePath()));
                            contactID++;
                        } while (cursor2.moveToNext());
                    }

                    Cursor cursor3 = db.query(TABLE_SETTINGS, new String[]{KEY_SIZE, KEY_COLOR}, KEY_ID + " = ?", new String[]{Integer.toString(newprofile.getID())}, null, null, null, null);
                    if (cursor3.moveToFirst()) {
                        newprofile.updateSettings(cursor3.getString(0), cursor3.getString(1));
                    }

                    list.add(newprofile);
                } while (cursor.moveToNext());

                return list;
            }
            return new ArrayList<>();
        } catch (Exception e) {
            Log.e("getAllProfiles", "Error: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            db.close();
        }
    }

    private boolean initializeProfile() {
        try {
            new File(_profile.getImagePath()).getParentFile().mkdirs();
            return true;
        } catch (Exception e) {
            Log.e("initializeProfile", "Error: " + e.getMessage());
            return false;
        }
    }

    private void Copy(String srcP, String dstP, boolean delete) {
        try {
            File src = new File(srcP);
            File dst = new File(dstP);
            dst.getParentFile().mkdirs();

            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

            if (delete) {
                src.delete();
                src.getParentFile().delete();
            }
        } catch (Exception e) {
            Log.e("copyFile", "Error: " + e.getMessage());
        }
    }
}