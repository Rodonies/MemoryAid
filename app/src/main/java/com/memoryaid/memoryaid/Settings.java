package com.memoryaid.memoryaid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;


public class Settings extends ActionBarActivity {

    public static final String PREFS_FIRST_LAUNCH = "MyPreferenceFiles";

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, Homescreen.class);
        startActivity(i);
        SharedPreferences CheckOnFirstLaunched = getSharedPreferences(PREFS_FIRST_LAUNCH,0);
        SharedPreferences.Editor editor = CheckOnFirstLaunched.edit();
        editor.putString("First_Launch","false");
        editor.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        themeUtils.onActivityCreateSetTheme(this);
        themeUtils.onActivityCreateSetColor(this);
        setContentView(R.layout.activity_settings);

        final RadioButton A = (RadioButton) findViewById(R.id.RadioSmall);
        final RadioButton B = (RadioButton) findViewById(R.id.RadioMedium);
        final RadioButton C = (RadioButton) findViewById(R.id.RadioBig);

        switch (themeUtils.cTheme) {
            case 1:
                A.setChecked(true);
                B.setChecked(false);
                C.setChecked(false);
                break;
            case 0:
                A.setChecked(false);
                B.setChecked(false);
                C.setChecked(true);
                break;
            case 2:
                A.setChecked(false);
                B.setChecked(true);
                C.setChecked(false);
                break;
        }

        final RadioButton m_one = (RadioButton) findViewById(R.id.RadioEnglish);
        final RadioButton m_two = (RadioButton) findViewById(R.id.RadioFrans);
        final RadioButton m_three = (RadioButton) findViewById(R.id.RadioDeutsch);
        final RadioButton m_four = (RadioButton) findViewById(R.id.RadioEspanol);


        m_one.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_one.setChecked(true);
                m_two.setChecked(false);
                m_three.setChecked(false);
                m_four.setChecked(false);
            }
        });

        m_two.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_one.setChecked(false);
                m_two.setChecked(true);
                m_three.setChecked(false);
                m_four.setChecked(false);
            }
        });

        m_three.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_one.setChecked(false);
                m_two.setChecked(false);
                m_three.setChecked(true);
                m_four.setChecked(false);
            }
        });

        m_four.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_one.setChecked(false);
                m_two.setChecked(false);
                m_three.setChecked(false);
                m_four.setChecked(true);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                themeUtils.changeToTheme(this, themeUtils.Medium);
                break;
            case R.id.RadioBig:
                if (checked)
                    themeUtils.changeToTheme(this, themeUtils.Big);
                break;
            case R.id.RadioSmall:
                if (checked)
                    themeUtils.changeToTheme(this, themeUtils.Small);
                break;
        }

    }

    public void onColorButtonClicked(View view) {

     switch (view.getId()) {
        case R.id.button2:
        themeUtils.ChangeToColor(this, themeUtils.blue);
        break;
        case R.id.button3:
        themeUtils.ChangeToColor(this, themeUtils.green);
        break;
        case R.id.button4:
        themeUtils.ChangeToColor(this, themeUtils.red);
        break;
        case R.id.button5:
        themeUtils.ChangeToColor(this, themeUtils.yellow);
        break;
        case R.id.button6:
        themeUtils.ChangeToColor(this, themeUtils.pink);
        break;
        case R.id.button7:
        themeUtils.ChangeToColor(this, themeUtils.purple);
        break;
        case R.id.button8:
        themeUtils.ChangeToColor(this, themeUtils.white);
        break;
        case R.id.button9:
            themeUtils.ChangeToColor(this, themeUtils.black);
        break;
    }}

    }

