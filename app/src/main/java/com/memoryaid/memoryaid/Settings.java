package com.memoryaid.memoryaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;


public class Settings extends ActionBarActivity {

    public static final String SaveData = "MyPreferenceFiles";
    private View V;
    private String First_Launch;
    private String Contact_Or_Profile;
    private String StateCheckbox;
    public boolean test;



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
        StateCheckbox = settings.getString("StateCheckbox","false");


        CheckBox Advanced = (CheckBox) findViewById(R.id.checkboxAdvanced);

        if (StateCheckbox == "true")
            Advanced.setChecked(true);
        else
            Advanced.setChecked(false);




        final RadioButton A = (RadioButton) findViewById(R.id.RadioSmall);
        final RadioButton B = (RadioButton) findViewById(R.id.RadioMedium);
        final RadioButton C = (RadioButton) findViewById(R.id.RadioBig);

                A.setChecked(false);
                B.setChecked(true);
                C.setChecked(false);



    }


    public void onRadioButtonLanguageClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

    }

    public void AddProfClicked(View view) {
        Log.e("test", "iets");
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();


        switch (view.getId()) {

            case R.id.RadioMedium:
                themeUtils.changeToTheme(this, "Medium");
                break;
            case R.id.RadioSmall:
                themeUtils.changeToTheme(this, "Small");
                break;
            case R.id.RadioBig:
                themeUtils.changeToTheme(this, "Big");
                break;
        }

    }

    public void onColorButtonClicked(View view) {

        switch (view.getId()) {
            case R.id.button2:
                themeUtils.ChangeToColor(this, "Blue");
                break;
            case R.id.button3:
                themeUtils.ChangeToColor(this, "green");
                break;
            case R.id.button4:
                themeUtils.ChangeToColor(this, "red");
                break;
            case R.id.button5:
                themeUtils.ChangeToColor(this, "yellow");
                break;
            case R.id.button6:
                themeUtils.ChangeToColor(this, "pink");
                break;
            case R.id.button7:
                themeUtils.ChangeToColor(this, "purple");
                break;
            case R.id.button8:
                themeUtils.ChangeToColor(this, "white");
                break;
            case R.id.button9:
                themeUtils.ChangeToColor(this, "black");
                break;
        }

    }

    public void AdvancedCheckboxChecked(View view){

        boolean checked = ((CheckBox) view).isChecked();

        Button button = (Button)findViewById(R.id.btnProfileManager);

        DatabaseHandler db = new DatabaseHandler(this);
        SharedPreferences settings = getSharedPreferences(SaveData, 0);
        SharedPreferences.Editor editor = settings.edit();


        if(checked)
            {
                test = true;
                editor.putString("StateCheckbox", "true");
                editor.commit();

            }
        else
            {
                test = false;
                editor.putString("StateCheckbox", "false");
                editor.commit();
            }



    }

}

