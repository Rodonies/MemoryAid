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
        _imagepath = "/" + _id + "_" + _firstname + "_" + _lastname;
        _contacts = new ArrayList<Contact>();
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

    public File getImageFile() {
        File image = new File("/data/data/com.memoryaid.memoryaid/files/" + _imagepath + "/image.png");
        if (image.exists())
            return image;
        else {
            return null;
        }
    }

    public String getImagePath() {
        return "/data/data/com.memoryaid.memoryaid/files/" + _imagepath + "/image.png";
    }

    public String getSize() {
        return _size;
    }

    public String getColor() {
        return _color;
    }

    public void updateSettings(String size, String color) {
        _size = size;
        _color = color;
    }
}
