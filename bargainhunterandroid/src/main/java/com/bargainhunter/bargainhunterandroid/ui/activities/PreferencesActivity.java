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
         * We need this to sync the seekBar with the editTextPreference as the values
         * cannot be accessed without SharedPreferences
         * When the value in the seekBar changes it will automatically change it in the
         * editText and vice versa
         * The user is currently only allowed to enter positive integers in the editText
         * In case the user enters a radius over the maximum allowed distance the seekBar
         * will show the maximum distance instead.
         */
        if ("editText".equals(key)) {
            String val = sharedPreferences.getString("editText", "50");
            if (val == null || val.trim().equals("")) {
                val = "" + R.string.default_radius; // <-- our default value
            }

            //setting the changed value to the other key in this case from the editText to the seekBar
            sharedPreferences.edit().putInt("notificationRadius", Integer.parseInt(val)).commit();

            //resetting screen, because it wouldn't update otherwise unless
            //you return to the previous screen and access this screen again
            setPreferenceScreen(null);
            addPreferencesFromResource(R.xml.preferences);

        }else if("notificationRadius".equals(key)) {
            String val = "" + sharedPreferences.getInt("notificationRadius",  50);
            if (val == null || val.trim().equals("")) {
                val = "" + R.string.default_radius; // <-- our default value
            }

            //setting the changed value to the other key
            sharedPreferences.edit().putString("editText", val).commit();

            //resetting screen
            setPreferenceScreen(null);
            addPreferencesFromResource(R.xml.preferences);

        }
    }

}