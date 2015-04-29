package com.memoryaid.memoryaid;

/**
 * Created by stijn on 6/02/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;

public class themeUtils {

    public static String cTheme = "Medium" ;

    public final static int Englisch = 0;
    public final static int Espagnol = 1;
    public final static int Nederlands = 2;
    public final static int Frans = 3;
    public static int cLanguage = 0;

    public static void changeToTheme(Activity activity, String theme) {
        DatabaseHandler db = new DatabaseHandler(activity);
        if (db.findProfile(DatabaseHandler.getProfile().getID()));
        {
            db.saveSettings(theme, "null");
        }
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (cTheme) {
            case "Big":
                activity.setTheme(R.style.Big);
                break;
            case "Small":
                activity.setTheme(R.style.Small);
                break;
            case "Medium":
                activity.setTheme(R.style.Medium);
                break;
        }
    }

    public static void changeToLanguage(Activity activity, int Language) {
        cLanguage = Language;
        if(cLanguage == 0)
        {


        }
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetLanguage(Activity activity) {
        switch (cLanguage) {
            case 0:
                //
                break;
            case 1:
                //
                break;
            case 2:
                //
            case 3:
                //
                break;
        }
    }

    public static void ChangeToColor(Activity activity, String color ) {
        DatabaseHandler db = new DatabaseHandler(activity);
        if (db.findProfile(DatabaseHandler.getProfile().getID()));
        {
            db.saveSettings("null", color);
        }
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetColor(Activity activity) {
        switch (DatabaseHandler.getProfile().getColor()) {
            case "Blue":
                activity.setTheme(R.style.Blue);
                break;
            case "red":
                activity.setTheme(R.style.Red);
                break;
            case "yellow":
                activity.setTheme(R.style.Yellow);
                break;
            case "green":
                activity.setTheme(R.style.Green);
                break;
            case "white":
                activity.setTheme(R.style.White);
                break;
            case "black":
                activity.setTheme(R.style.Black);
                break;
            case "purple":
                activity.setTheme(R.style.Purple);
                break;
            case "pink":
                activity.setTheme(R.style.Pink);
                break;
        }
    }




}
