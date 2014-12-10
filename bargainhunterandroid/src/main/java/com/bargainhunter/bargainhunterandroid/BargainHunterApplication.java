package com.bargainhunter.bargainhunterandroid;

import android.app.Application;
import com.activeandroid.ActiveAndroid;
import com.bargainhunter.bargainhunterandroid.controllers.DatabaseController;

/**
 * Created by Achilleas Naoumidis on 12/9/14.
 */
public class BargainHunterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        this.deleteDatabase("bargain_hunter.db");

        ActiveAndroid.initialize(this);

        DatabaseController.updateDatabase(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        ActiveAndroid.dispose();
    }
}
