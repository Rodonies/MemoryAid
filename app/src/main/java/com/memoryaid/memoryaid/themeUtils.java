package com.memoryaid.memoryaid;

/**
 * Created by stijn on 6/02/2015.
 */

import android.app.Activity;
import android.content.Intent;

public class themeUtils {
    public final static int Big = 0;
    public final static int Small = 1;
    public final static int Medium = 2;
    public static int cTheme = 2;

    public final static char blue = 'b';
    public final static char red = 'r';
    public final static char yellow = 'y';
    public final static char green = 'g';
    public final static char black = 'l';
    public final static char white = 'w';
    public final static char pink = 'p';
    public final static char purple = 'z';
    public static int cColor = 'b';

    public static void changeToTheme(Activity activity, int theme) {
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (cTheme) {
            case Big:
                activity.setTheme(R.style.Big);
                break;
            case Small:
                activity.setTheme(R.style.Small);
                break;
            case Medium:
                activity.setTheme(R.style.Medium);
                break;
        }
    }

    public static void ChangeToColor(Activity activity, char color) {
        cColor = color;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetColor(Activity activity) {
        switch (cColor) {
            case blue:
                activity.setTheme(R.style.Blue);
                break;
            case red:
                activity.setTheme(R.style.Red);
                break;
            case yellow:
                activity.setTheme(R.style.Yellow);
                break;
            case green:
                activity.setTheme(R.style.Green);
                break;
            case white:
                activity.setTheme(R.style.White);
                break;
            case black:
                activity.setTheme(R.style.Black);
                break;
            case purple:
                activity.setTheme(R.style.Purple);
                break;
            case pink:
                activity.setTheme(R.style.Pink);
                break;
        }
    }


}
