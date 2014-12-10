package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.bargainhunter.bargainhunterandroid.R;


/**
 * Created by Veruz on 6/12/2014.
 */
public class PreferencesActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}