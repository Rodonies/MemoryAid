package com.memoryaid.memoryaid;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 2/03/2015.
 */
public class Profile {
    private Integer _id;
    private String _firstname;
    private String _lastname;
    private String _birthdate;
    private String _number;
    private String _information;
    private String _imagepath;
    private ArrayList<Contact> _contacts;

    private String _color;
    private String _size;

    public Profile(String firstname, String lastname, String birthdate, String number, String information) {
        _firstname = firstname;
        _lastname = lastname;
        _birthdate = birthdate;
        _number = number;
        _information = information;
        _contacts = new ArrayList<Contact>();
    }

    public Profile(Integer id, String firstname, String lastname, String birthdate, String number, String information) {
        _id = id;
        _firstname = firstname;
        _lastname = lastname;
        _birthdate = birthdate;
        _number = number;
        _information = information;
        _imagepath = _id + "_" + _firstname + "_" + _lastname;
        _contacts = new ArrayList<Contact>();
    }

    public void Show() {
        Log.e("Profile", getID() + ": " + getFullName());
        Log.e("Profile", getNumber());

        Log.e("Profile", "size: " + getSize());
        Log.e("Profile", "color: " + getColor());

        for (Contact contact : getContacts()) {
            Log.e("ShowContacts", "Contact " + contact.getCID() + ": " + contact.getFullName() + " " + contact.getNumber());
        }
    }

    public void addContact(Contact contact) {
        _contacts.add(contact);
    }

    public Integer getID() {
        return _id;
    }

    public String getFullName() {
        return _firstname + " " + _lastname;
    }

    public String getFirstName() {
        return _firstname;
    }

    public String getLastName() {
        return _lastname;
    }

    public String getBirthDate() {
        return _birthdate;
    }

    public String getNumber() {
        return _number;
    }

    public String getInformation() {
        return _information;
    }

    public ArrayList<Contact> getContacts() {
        return _contacts;
    }

    public Uri getImage() {
        File image = new File(_imagepath + "/image.png");
        if (image.exists()) return Uri.fromFile(image);
        else return Uri.fromFile(new File("@drawable/defaultimage.gif"));
    }

    /*public Uri saveImage() {
        return Uri(_imagepath + "/image.png");
    }*/

    public String getImagePath() {
        return _imagepath;
    }

    public String getSize() {
        return _size;
    }

    public String getColor() {
        return _color;
    }

    public void updateImagePath(String newpath) {
        _imagepath = newpath;
    }

    public void updateSettings(String size, String color) {
        _size = size;
        _color = color;
    }

    public boolean settingsInitialized() {
        if (_size == null || _color == null) return false;
        else return true;
    }
}
