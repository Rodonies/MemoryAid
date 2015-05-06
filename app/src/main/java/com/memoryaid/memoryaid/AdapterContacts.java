package com.memoryaid.memoryaid;

import android.content.Context;
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
        Uri test = contact.getImageUri();

        ContactImage.setImageURI(test);

        // Picasso.with(getContext()).load(R.drawable.defaultimage).resize(150, 150).into(ContactImage);
        return convertView;
    }

    public AdapterContacts(Context context, ArrayList<Contact> contactlist) {
        super(context, 0, contactlist);
    }
}
