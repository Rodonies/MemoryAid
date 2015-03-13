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
}
