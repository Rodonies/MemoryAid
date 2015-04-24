package com.memoryaid.memoryaid;

import android.content.Context;
import android.net.Uri;
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
class AdapterContacts extends ArrayAdapter<Contact> {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);

        convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.contactlist_normalsize, parent, false);

        TextView ContactName = (TextView) convertView.findViewById(R.id.TextName);
        ContactName.setText(contact.getFullName());

        ImageView ContactImage = (ImageView) convertView.findViewById(R.id.imgContact);
        ContactImage.setImageURI(Uri.parse(contact.getImagePath()));

        return convertView;
    }

    public AdapterContacts(Context context, ArrayList<Contact> contactlist) {
        super(context, 0, contactlist);
    }
}
