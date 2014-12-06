package com.bargainhunter.bargainhunterandroid;

import android.os.Bundle;
import android.preference.PreferenceActivity;


/**
 * Created by Veruz on 6/12/2014.
 */
public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}