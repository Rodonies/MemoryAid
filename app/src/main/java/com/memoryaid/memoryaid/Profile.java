package com.memoryaid.memoryaid;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 2/03/2015.
 */
public class Profile {
    private Integer _id;
    private String _firstname;
    private String _lastname;
    private String _number;
    private String _imagepath;
    private ArrayList<Contact> _contacts;

    private String _language;
    private String _color;
    private String _size;

    public Profile(String firstname, String lastname, String number) {
        _firstname = firstname;
        _lastname = lastname;
        _number = number;
        _contacts = new ArrayList<Contact>();
    }

    public Profile(Integer id, String firstname, String lastname, String number) {
        _id = id;
        _firstname = firstname;
        _lastname = lastname;
        _number = number;
        _imagepath = _id + "_" + _firstname + "_" + _lastname;
        _contacts = new ArrayList<Contact>();
    }

    public void Show() {
        Log.e("Profile", getID() + ": " + getFullName());
        Log.e("Profile", getNumber());

        if (_size != null) Log.e("Profile", getSize());
        if (_color != null) Log.e("Profile", getColor());
        if (_language != null) Log.e("Profile", getLanguage());

        for (Contact contact : getContacts()) {
            Log.e("ShowContacts", "Contact " + contact.getID() + ": " + contact.getFullName() + " " + contact.getNumber());
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

    public String getNumber() {
        return _number;
    }

    public List<Contact> getContacts() {
        return _contacts;
    }

    public String getImagePath() {
        return _imagepath;
    }

    public String getSize() {
        return _size;
    }

    public String getColor() {
        return _color;
    }

    public String getLanguage() {
        return _language;
    }

    public void updateImagePath(String newpath) {
        _imagepath = newpath;
    }

    public void updateSettings(String size, String color, String language) {
        _size = size;
        _color = color;
        _language = language;
    }

    public boolean settingsInitialized() {
        if (_size == null || _color == null || _language == null) return false;
        else return true;
    }
}
