package com.memoryaid.memoryaid;

import java.util.List;

/**
 * Created by Matthew on 2/03/2015.
 */
public class Contact {
    private Integer _id;
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

    public Contact(Integer id, String firstname, String lastname, String birthdate, String relation, String number, String information, String profilepath) {
        _id = id;
        _firstname = firstname;
        _lastname = lastname;
        _birthdate = birthdate;
        _relation = relation;
        _number = number;
        _information = information;
        _imagepath = profilepath + "/" + _id + "_" + _firstname + "_" + _lastname;
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

    public String getRelation() {
        return _relation;
    }

    public String getNumber() {
        return _number;
    }

    public String getInformation() {
        return _information;
    }

    public String getImagePath() {
        return _imagepath;
    }
}
