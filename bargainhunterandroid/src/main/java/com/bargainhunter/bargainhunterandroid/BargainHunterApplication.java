package com.bargainhunter.bargainhunterandroid;

import android.app.Application;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.bargainhunter.bargainhunterandroid.controllers.DatabaseController;
import com.bargainhunter.bargainhunterandroid.models.entities.*;

/**
 * Created by Achilleas Naoumidis on 12/9/14.
 */
public class BargainHunterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        this.deleteDatabase("bargain_hunter.db");

        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(Offer.class);
        configurationBuilder.addModelClasses(Branch.class);
        configurationBuilder.addModelClasses(Category.class);
        configurationBuilder.addModelClasses(OfferSubcategory.class);
        configurationBuilder.addModelClasses(Store.class);
        configurationBuilder.addModelClasses(Subcategory.class);

        ActiveAndroid.initialize(configurationBuilder.create());

//        DatabaseController.updateDatabaseCategories(this);
//        DatabaseController.updateDatabase(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        ActiveAndroid.dispose();
    }
}
