package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import com.bargainhunter.bargainhunterandroid.ui.fragments.PreferencesFragment;

/**
 * Created by Veruz on 6/12/2014.
 */

public class PreferencesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Calls the PreferencesFragment
         */
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PreferencesFragment()).commit();
    }


}