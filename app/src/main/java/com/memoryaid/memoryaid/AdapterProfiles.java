package com.memoryaid.memoryaid;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bart on 26/04/2015.
 */
public class AdapterProfiles extends ArrayAdapter<Profile> {

    public View getView(int position, View convertView, ViewGroup parent) {
        Profile profile = getItem(position);

        convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.contactlist_normalsize, parent, false);

        TextView ContactName = (TextView) convertView.findViewById(R.id.TextName);
        ContactName.setText(profile.getFullName());

        ImageView ContactImage = (ImageView) convertView.findViewById(R.id.imgContact);
        ContactImage.setImageURI(Uri.parse(profile.getImagePath()));

        return convertView;
    }

    public AdapterProfiles(Context context, ArrayList<Profile>Profilelist) {
        super(context, 0, Profilelist);
    }

}
