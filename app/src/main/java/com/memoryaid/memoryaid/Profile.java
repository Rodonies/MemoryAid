package com.memoryaid.memoryaid;

import android.util.Log;

import java.util.List;

/**
 * Created by Matthew on 2/03/2015.
 */
public class Profile {
    public String Name;
    public String Password;
    public String Birthday;
    public List<Contact> Contacts;

    public Profile(String name, String password, String birthday, List<Contact> list) {
        Name = name;
        Password = password;
        Birthday = birthday;
        Contacts = list;
    }

    public void ShowContacts() {
        for (Contact contact : Contacts) {
            Log.e("ShowContacts", "Contact: " + contact.Name);
        }
    }

}
