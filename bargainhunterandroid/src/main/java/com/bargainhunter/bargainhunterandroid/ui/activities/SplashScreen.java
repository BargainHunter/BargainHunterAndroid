package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.activeandroid.query.Select;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.controllers.DatabaseController;
import com.bargainhunter.bargainhunterandroid.controllers.LocationController;
import com.bargainhunter.bargainhunterandroid.models.Coordinates;
import com.bargainhunter.bargainhunterandroid.models.entities.Store;

public class SplashScreen extends Activity {
    //creates a ViewSwitcher object, to switch between Views
    private ViewSwitcher viewSwitcher;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Coordinates phoneLoc = new LocationController().findCoordinates(getApplicationContext());

        //Initialize a updateFromServerTask object and call the execute() method
        new updateFromServerTask(phoneLoc).execute();

    }

    //To use the AsyncTask, it must be subclassed
    private class updateFromServerTask extends AsyncTask<Void, Integer, Void>
    {
        private Coordinates phoneLoc;

        public updateFromServerTask(Coordinates phoneLoc) {
            this.phoneLoc = phoneLoc;
        }

        //A TextView object and a ProgressBar object
        private TextView textView;
        private ProgressBar progressBar;

        //Before running code in separate thread
        @Override
        protected void onPreExecute() {
            //Initialize the ViewSwitcher object
            viewSwitcher = new ViewSwitcher(SplashScreen.this);
            /* Initialize the loading screen with data from the 'activity_splash_screen.xml' layout xml file.
             * Add the initialized View to the viewSwitcher.*/
            viewSwitcher.addView(ViewSwitcher.inflate(SplashScreen.this, R.layout.activity_splash_screen, null));

            //Initialize the TextView and ProgressBar instances - IMPORTANT: call findViewById() from viewSwitcher.
            textView = (TextView) viewSwitcher.findViewById(R.id.splashScreenTextView);
            progressBar = (ProgressBar) viewSwitcher.findViewById(R.id.progressBar);

            progressBar.setIndeterminate(true);
            //Set ViewSwitcher instance as the current View.
            setContentView(viewSwitcher);
        }

        //The code to be executed in a background thread.
        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                //Get the current thread's token
                synchronized (this) {
                    DatabaseController.updateDatabaseCategories(getApplicationContext());
                    DatabaseController.updateDatabase(getApplicationContext(), phoneLoc);

                    //Initialize an integer (to know if stores are loaded)
                    int storeCount = new Select().from(Store.class).count();
                    while(storeCount==0) {
                        //Is there any store?
                        storeCount = new Select().from(Store.class).count();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result)
        {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            // close this activity
            finish();
        }
    }
}
