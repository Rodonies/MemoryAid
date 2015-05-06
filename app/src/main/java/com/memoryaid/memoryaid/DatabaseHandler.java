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
import java.lang.reflect.Array;
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
    //Profiel niet gevonden dus aanmaken
    db.addProfile(new Profile("naam", "achternaam", "geboortedatum", "nummer", "note"));
}


//volgende code is een voorbeeld voor de achternaam van het tweede profiel te veranderen naar "Jansens"

DatabaseHandler db = new DatabaseHandler(this);
if (db.findProfile(2))
{
    db.editProfile(null, "Jansens", null, null);
}

//volgende code is een voorbeeld om de voornaam van het profiel met de naam "Jos Joskens"
//die geen contacten heeft te veranderen naar "Jef" en daarnaa een contact met de naam "Jos Joskens" toe te voegen

DatabaseHandler db = new DatabaseHandler(this);
if (db.findProfile("Jos", "Joskens"))
{
    db.getProfile(); //Geeft profiel met naam "Jos Joskens" zonder contacten terug
    db.editProfile("Jef", null , null);
    db.addContact(new Contact("Jos", "Joskens", "relatie", "nummer", "informatie");
    db.getProfile(); //Geeft profiel met naam "Jef Joskens" met 1 contact, namelijk "Jos Joskens"
}


//volgende code verwijdert het eerste profiel, inclusief contacten en fotos

DatabaseHandler db = new DatabaseHandler(this);
if (findprofile(1)) db.deleteProfile();


//Volgende code voegt settings toe aan het eerste profiel
db.saveSettings("Big", "Rood");


//volgende code dient voor te zien of de settings bestaan of niet, als ze niet bestaan moet de settings intent worden opgestart
if (!db.getProfile().settingsInitialized()) StartNieuweSettingsIntentHier();

*/

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 30;
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
    private static String dir;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dir = context.getFilesDir().getPath();
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
            values.put(KEY_INFORMATION,profile.getInformation());

            db.insert(TABLE_PROFILES, null, values);
            db.close();

            if (findProfile(profile.getFirstName(), profile.getLastName())) {
                boolean b = saveSettings("Medium", "Blue");
                return findProfile(profile.getFirstName(), profile.getLastName());
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
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_PROFILEID, _profile.getID());
            values.put(KEY_FIRSTNAME, contact.getFirstName());
            values.put(KEY_LASTNAME, contact.getLastName());
            values.put(KEY_DATE, contact.getBirthDate());
            values.put(KEY_RELATION, contact.getRelation());
            values.put(KEY_NUMBER, contact.getNumber());
            values.put(KEY_INFORMATION, contact.getInformation());
            String path = _profile.getImagePath() + "/" + contact.getID() + "_" + contact.getFirstName() + "_" + contact.getLastName();

            new File(path).mkdirs();

            db.insert(TABLE_CONTACTS, null, values);
            db.close();
            return true;
        } catch (Exception e) {
            Log.e("addContact", "Error: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }


    public boolean findProfile(Integer id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_PROFILES, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_DATE, KEY_NUMBER, KEY_INFORMATION}, KEY_ID + " = ?", new String[]{Integer.toString(id)}, null, null, null, null);
            if (cursor.moveToFirst()) {
                _profile = new Profile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                db.close();
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
        }
    }

    public boolean findProfile(String firstname, String lastname) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(TABLE_PROFILES, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_DATE, KEY_NUMBER, KEY_INFORMATION}, KEY_FIRSTNAME + " = ? AND " + KEY_LASTNAME + " = ?", new String[]{firstname, lastname}, null, null, null, null);
            if (cursor.moveToFirst()) {
                _profile = new Profile(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                db.close();
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
        if (_profile.getFirstName() == null) {
            Profile prof = new Profile(null, null, null, null, null, null);
            prof.updateSettings("Medium","Blue");
            return prof;
        }
        else return _profile;
    }

    public boolean editProfile(String newfirstname, String newlastname, String newdate, String newnumber) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            if (newfirstname == null) newfirstname = _profile.getFirstName();
            if (newlastname == null) newfirstname = _profile.getFirstName();
            if (newdate == null) newdate = _profile.getBirthDate();
            if (newnumber == null) newfirstname = _profile.getFirstName();
            String oldpath = _profile.getImagePath();
            _profile.updateImagePath(_profile.getID() + "_" + newfirstname + "_" + newlastname);

            new File(oldpath).renameTo(new File(_profile.getImagePath()));

            ContentValues values = new ContentValues();
            values.put(KEY_FIRSTNAME, newfirstname);
            values.put(KEY_LASTNAME, newlastname);
            values.put(KEY_DATE, newdate);
            values.put(KEY_NUMBER, newnumber);

            if (db.update(TABLE_PROFILES, values, KEY_ID + " = ?", new String[]{String.valueOf(_profile.getID())}) == 1)
                return true;
            else return false;
        } catch (Exception e) {
            Log.e("editProfile", "Error: " + e.getMessage());
            return false;
        }
    }

    public boolean saveSettings(String size, String color) {
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ID, _profile.getID());

            if (size == null) values.put(KEY_SIZE,_profile.getSize()); else values.put(KEY_SIZE, size);
            if (color == null) values.put(KEY_COLOR,_profile.getColor()); else values.put(KEY_COLOR, color);

            if (loadSettings()) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.update(TABLE_SETTINGS, values, KEY_ID + " = ?", new String[]{String.valueOf(_profile.getID())});
                db.close();
            } else {
                SQLiteDatabase db = this.getWritableDatabase();
                db.insert(TABLE_SETTINGS, null, values);
                db.close();
            }
            loadSettings();
            return true;
        } catch (Exception e) {
            Log.e("saveSettings", "Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteContact(Contact contact) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            if (db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[]{String.valueOf(contact.getID())}) != 1)
                return false;
            new File(contact.getImagePath()).delete();
            db.close();
            return true;
        } catch (Exception e) {
            Log.e("deleteContact", "Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProfile() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            if (db.delete(TABLE_PROFILES, KEY_ID + " = ?", new String[]{String.valueOf(_profile.getID())}) == 0)
                return false;
            if (db.delete(TABLE_CONTACTS, KEY_PROFILEID + " = ?", new String[]{String.valueOf(_profile.getID())}) == 0)
                return false;
            if (db.delete(TABLE_SETTINGS, KEY_ID + " = ?", new String[]{String.valueOf(_profile.getID())}) == 0)
                return false;
            new File(_profile.getImagePath()).delete();
            _profile = null;
            db.close();
            return true;
        } catch (Exception e) {
            Log.e("deleteProfile", "Error: " + e.getMessage());
            return false;
        }
    }

    private boolean updateContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_DATE, KEY_RELATION, KEY_NUMBER, KEY_INFORMATION}, KEY_PROFILEID + " = ?", new String[]{Integer.toString(_profile.getID())}, null, null, null, null);

            int i = 1;
            if (cursor.moveToFirst()) {
                do {
                    _profile.addContact(new Contact(cursor.getInt(0), i, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), _profile.getImagePath()));
                    i++;
                } while (cursor.moveToNext());
                db.close();
                return true;
            }
            db.close();
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
                db.close();
                return true;
            }
            db.close();
            return false;
        } catch (Exception e) {
            Log.e("loadSettings", "Error: " + e.getMessage());
            db.close();
            return false;
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
                    int i = 1;
                    if (cursor2.moveToFirst()) {
                        do {
                            newprofile.addContact(new Contact(cursor2.getInt(0), i, cursor2.getString(1), cursor2.getString(2), cursor2.getString(3), cursor2.getString(4), cursor2.getString(5), cursor2.getString(6), newprofile.getImagePath()));
                            i++;
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
            db.close();
            return new ArrayList<Profile>();
        } catch (Exception e) {
            Log.e("getAllProfiles", "Error: " + e.getMessage());
            db.close();
            return new ArrayList<Profile>();
        }
    }

    private boolean initializeProfile() {
        try {
            String path = dir + "//" + _profile.getID() + "_" + _profile.getFirstName() + "_" + _profile.getLastName();
            new File(path).mkdirs();
            return true;
        } catch (Exception e) {
            Log.e("initializeProfile", "Error: " + e.getMessage());
            return false;
        }
    }
}