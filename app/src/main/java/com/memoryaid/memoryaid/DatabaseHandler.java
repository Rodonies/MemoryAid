package com.memoryaid.memoryaid;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.memoryaid.memoryaid.Contact;
import com.memoryaid.memoryaid.Profile;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database";

    private static final String TABLE_PROFILES = "profiles";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_SETTINGS = "settings";

    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_IMAGEPATH = "imagepath";

    private static final String KEY_RELATION = "relation";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_INFORMATION = "information";

    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_SIZE = "size";
    private static final String KEY_COLOR = "color";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILES_TABLE =
                "CREATE TABLE `" + TABLE_PROFILES + "` (\n" +
                        "\t`" + KEY_ID + "`\tINTEGER NOT NULL,\n" +
                        "\t`" + KEY_FIRSTNAME + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_LASTNAME + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_IMAGEPATH + "`\tTEXT NOT NULL,\n" +
                        "\tPRIMARY KEY(" + KEY_ID + ")\n" +
                        ");";

        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE `" + TABLE_CONTACTS + "` (\n" +
                        "\t`" + KEY_ID + "`\tINTEGER NOT NULL,\n" +
                        "\t`" + KEY_FIRSTNAME + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_LASTNAME + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_RELATION + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_NUMBER + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_INFORMATION + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_IMAGEPATH + "`\tTEXT NOT NULL\n" +
                        ");";

        String CREATE_SETTINGS_TABLE =
                "CREATE TABLE `" + TABLE_SETTINGS + "` (\n" +
                        "\t`" + KEY_ID + "`\tINTEGER NOT NULL,\n" +
                        "\t`" + KEY_LANGUAGE + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_SIZE + "`\tTEXT NOT NULL,\n" +
                        "\t`" + KEY_COLOR + "`\tTEXT NOT NULL,\n" +
                        ");";

        db.execSQL(CREATE_PROFILES_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_SETTINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);

        onCreate(db);
    }

    // Adding new contact
    void addContact(Profile profile, Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, contact.getFirstName());
        values.put(KEY_LASTNAME, contact.getLastName());
        values.put(KEY_NUMBER, contact.getNumber());
        values.put(KEY_FIRSTNAME, contact.getNumber());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    /*Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2));

        return contact;
    }

    // Getting All Contacts
    /*public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }*/

}