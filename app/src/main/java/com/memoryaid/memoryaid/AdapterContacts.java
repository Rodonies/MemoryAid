package com.memoryaid.memoryaid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Created by Bart on 24/04/2015.
 */



class AdapterContacts extends ArrayAdapter<Contact>{

    Context context;
    int layoutid;
    ArrayList<Contact> contactlist;
    Contact bufferContact;
    TextView ContactName;
    ImageView ContactImage;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.contactlist_normalsize,parent,false);

       /* LayoutInflater theInflator = LayoutInflater.from(getContext());
        View theView = theInflator.inflate(layoutid,parent,false);*/
        bufferContact = getItem(position);
        ContactName = (TextView) ContactName.findViewById(R.id.TextName);
        ContactName.setText(bufferContact.getFirstName());



        //ContactImage = (ImageView) ContactImage.findViewById(R.id.imgContact);
        //Uri photobuffer = Uri.parse(bufferContact.getImagePath());
        //ContactImage.setImageURI(photobuffer);
        return convertView;
    }

    public AdapterContacts(Context context,int layoutid,  ArrayList<Contact> contactlist) {
        super(context,R.layout.contactlist_normalsize,contactlist);

        this.layoutid = layoutid;
        this.context = context;

        for(Contact contact: contactlist)
        {
            this.contactlist.add(contact);
        }

    }
}
