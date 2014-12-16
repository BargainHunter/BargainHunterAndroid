package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import com.bargainhunter.bargainhunterandroid.R;


/**
 * Created by Veruz on 6/12/2014.
 */


public class PreferencesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set up a listener whenever a key changes
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /**
         * we need this to sync the seekbar with the editText
         * when the value in the seekbar changes it will automatically change it in the
         * editText and vice versa
         */
        if ("editText".equals(key)) {
            String val = sharedPreferences.getString("editText", "50");
            if (val == null || val.trim().equals("")) {
                val = "" + R.string.default_radius; // <-- our default value
            }

            //setting value to the other key
            sharedPreferences.edit().putInt("notificationRadius", Integer.parseInt(val)).commit();

            //resetting screen
            setPreferenceScreen(null);
            addPreferencesFromResource(R.xml.preferences);

        }else if("notificationRadius".equals(key)) {
            String val = "" + sharedPreferences.getInt("notificationRadius",  50);
            if (val == null || val.trim().equals("")) {
                val = "" + R.string.default_radius; // <-- our default value
            }

            //setting value to the other key
            sharedPreferences.edit().putString("editText", val).commit();

            //resetting screen
            setPreferenceScreen(null);
            addPreferencesFromResource(R.xml.preferences);

        }
    }

}