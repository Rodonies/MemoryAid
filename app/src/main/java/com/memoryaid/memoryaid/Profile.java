package com.memoryaid.memoryaid;

import android.util.Log;

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
    private List<Contact> _contacts;

    public Profile(String firstname, String lastname, String number) {
        _firstname = firstname;
        _lastname = lastname;
        _number = number;
    }

    public Profile(Integer id, String firstname, String lastname, String number, List<Contact> list, String imagepath) {
        _id = id;
        _firstname = firstname;
        _lastname = lastname;
        _number = number;
        _contacts = list;
        _imagepath = imagepath;
    }

    public void ShowContacts() {
        for (Contact contact : _contacts) {
            Log.e("ShowContacts", "Contact: " + contact.getFullName());
        }
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
}
