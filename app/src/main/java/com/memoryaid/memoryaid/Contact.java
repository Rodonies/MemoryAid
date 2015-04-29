package com.memoryaid.memoryaid;

import android.net.Uri;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * Created by Matthew on 2/03/2015.
 */
public class Contact {
    private Integer _aid;
    private Integer _cid;
    private String _firstname;
    private String _lastname;
    private String _birthdate;
    private String _relation;
    private String _number;
    private String _information;
    private String _imagepath;

    public Contact(String firstname, String lastname, String birthdate, String relation, String number, String information) {
        _firstname = firstname;
        _lastname = lastname;
        _birthdate = birthdate;
        _relation = relation;
        _number = number;
        _information = information;
    }

    public Contact(Integer aid, Integer cid, String firstname, String lastname, String birthdate, String relation, String number, String information, String profilepath) {
        _aid = aid;
        _cid = cid;
        _firstname = firstname;
        _lastname = lastname;
        _birthdate = birthdate;
        _relation = relation;
        _number = number;
        _information = information;
        _imagepath = profilepath + "/" + _aid + "_" + _firstname + "_" + _lastname;
    }

    public Integer getID() {
        return _aid;
    }

    public Integer getCID() {
        return _cid;
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

    public String getRelation() {
        return _relation;
    }

    public String getNumber() {
        return _number;
    }

    public String getInformation() {
        return _information;
    }

    public Uri getImageUri() {
        File image = new File(_imagepath + "/image.png");
        if (image.exists())
            return Uri.fromFile(image);
        else
        {
            Uri DrawableToUri = Uri.parse("android.resource://com.memoryaid.memoryaid/drawable/defaultimage.gif");
            return  DrawableToUri;


        }

    }

    public File getImageFile() {
        return new File(_imagepath + "/image.png");
    }

    public String getImagePath() {
        return _imagepath;
    }
}
