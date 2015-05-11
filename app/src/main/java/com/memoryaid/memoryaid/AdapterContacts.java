package com.memoryaid.memoryaid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Bart on 24/04/2015.
 */
class AdapterContacts extends ArrayAdapter<Contact> {
    @Override


    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);

        DatabaseHandler db = new DatabaseHandler(getContext());


        if(db.getProfile().getSize().equals("Medium"))
        {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.contactlist_normalsize, parent, false);
        }
        else if(db.getProfile().getSize().equals("Big"))
        {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.contactlist_bigsize, parent, false);
        }
        else if(db.getProfile().getSize().equals("Small"))
        {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.contactlist_smallsize, parent, false);
        }


        TextView ContactName = (TextView) convertView.findViewById(R.id.TextName);
        ContactName.setText(contact.getFullName());

        ImageView ContactImage = (ImageView) convertView.findViewById(R.id.imgContact);

        if(db.getProfile().getSize().equals("Medium"))
        {
            if (contact.getImageFile() == null)
                Picasso.with(getContext()).load(R.drawable.defaultimage).resize(300, 350).into(ContactImage);
            else
                Picasso.with(getContext()).load(contact.getImageFile()).resize(300, 350).into(ContactImage);

        }
        else if(db.getProfile().getSize().equals("Big"))
        {
            if (contact.getImageFile() == null)
                Picasso.with(getContext()).load(R.drawable.defaultimage).resize(450, 500).into(ContactImage);
            else
                Picasso.with(getContext()).load(contact.getImageFile()).resize(450, 500).into(ContactImage);

        }
        else if(db.getProfile().getSize().equals("Small"))
        {
            if (contact.getImageFile() == null)
                Picasso.with(getContext()).load(R.drawable.defaultimage).resize(150, 200).into(ContactImage);
            else
                Picasso.with(getContext()).load(contact.getImageFile()).resize(150, 200).into(ContactImage);
        }
        return convertView;

    }



    public AdapterContacts(Context context, ArrayList<Contact> contactlist) {
        super(context, 0, contactlist);
    }
}
