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
    private String _imagepath;
    private List<Contact> _contacts;

    public Profile(Integer id, String firstname, String lastname, List<Contact> list) {
        _id = _id;
        _firstname = firstname;
        _lastname = lastname;
        _contacts = list;
    }

    public void ShowContacts() {
        for (Contact contact : _contacts) {
            Log.e("ShowContacts", "Contact: " + contact.getFullName());
        }
    }

    public Integer getID()
    {
        return _id;
    }

    public String getFullName()
    {
        return _firstname + " " + _lastname;
    }

    public String getFirstName()
    {
        return _firstname;
    }

    public String getLastName()
    {
        return _lastname;
    }

    public List<Contact> getContacts()
    {
        return _contacts;
    }
}
