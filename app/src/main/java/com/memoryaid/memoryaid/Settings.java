package com.memoryaid.memoryaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;


public class Settings extends ActionBarActivity {

    public static final String SaveData = "MyPreferenceFiles";
    private View V;
    private String First_Launch;
    private String Contact_Or_Profile;
    private boolean StateCheckbox;
    private CheckBox Advanced;
    private RadioButton A;
    private RadioButton B;
    private RadioButton C;

    @Override
    public void onBackPressed() {
        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        Intent i = new Intent(this, Homescreen.class);
        startActivity(i);

    }

    public void CreateNewUser(View view) {
        Intent i = new Intent(this, CreateNewUser.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        StateCheckbox = settings.getBoolean("StateCheckbox", false);

        Advanced = (CheckBox) findViewById(R.id.checkboxAdvanced);
        if (StateCheckbox == true)
            Advanced.setChecked(true);
        else
            Advanced.setChecked(false);

        A = (RadioButton) findViewById(R.id.RadioSmall);
        B = (RadioButton) findViewById(R.id.RadioMedium);
        C = (RadioButton) findViewById(R.id.RadioBig);

        DatabaseHandler db = new DatabaseHandler(this);

        if(db.getProfile().getSize().equals("Medium"))
        {
            A.setChecked(false);
            B.setChecked(true);
            C.setChecked(false);
        }
        else if(db.getProfile().getSize().equals("Small"))
        {
            A.setChecked(true);
            B.setChecked(false);
            C.setChecked(false);
        }
        else if(db.getProfile().getSize().equals("Big"))
        {
                A.setChecked(false);
                B.setChecked(false);
                C.setChecked(true);
        }
     }


    public void onRadioButtonLanguageClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

    }

    public void AddProfClicked(View view) {
        Log.e("test", "iets");
    }


    public void onRadioButtonClicked(View view) {

        DatabaseHandler db = new DatabaseHandler(this);

        if (A.isChecked()) {
            db.saveSettings("Small",null);
        }
        else if(B.isChecked()) {
            db.saveSettings("Medium",null);
        }
        else if(C.isChecked()) {
            db.saveSettings("Big",null);
        }
    }

    public void onColorButtonClicked(View view) {

        switch (view.getId()) {
            case R.id.button2:
                themeUtils.ChangeToColor(this, "Blue");
                break;
            case R.id.button3:
                themeUtils.ChangeToColor(this, "Green");
                break;
            case R.id.button4:
                themeUtils.ChangeToColor(this, "Red");
                break;
            case R.id.button5:
                themeUtils.ChangeToColor(this, "Yellow");
                break;
            case R.id.button6:
                themeUtils.ChangeToColor(this, "Pink");
                break;
            case R.id.button7:
                themeUtils.ChangeToColor(this, "Purple");
                break;
            case R.id.button8:
                themeUtils.ChangeToColor(this, "White");
                break;
            case R.id.button9:
                themeUtils.ChangeToColor(this, "Black");
                break;
        }

    }

    public void AdvancedCheckboxChecked(View view) {

        DatabaseHandler db = new DatabaseHandler(this);
        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        SharedPreferences.Editor editor = settings.edit();


        if (Advanced.isChecked()) {

            editor.putBoolean("StateCheckbox", true);
            editor.commit();

        } else {

            editor.putBoolean("StateCheckbox", false);
            editor.commit();
        }
    }

}

