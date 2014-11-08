package com.bargainhunter.bargainhunterandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import gr.teicm.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.controllers.LocationController;
import com.bargainhunter.bargainhunterandroid.gps.GPSTracker;
import com.bargainhunter.bargainhunterandroid.models.Coordinates;


public class MainActivity extends ActionBarActivity {
    Button btnShowLocation;
    private GPSTracker gps;
    TextView locationView;
    Coordinates phoneLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnShowLocation= (Button) findViewById(R.id.btnShowLocation);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gps = new GPSTracker(MainActivity.this);
                LocationController controller=new LocationController();
                phoneLoc=controller.findCoordinates(gps);
                updateDisplay();

            }
        });


    }
    protected void updateDisplay() {

        locationView = (TextView) findViewById(R.id.locationView);
        locationView.setText("lat: "+phoneLoc.getLatitude()+"\n"+"long: "+phoneLoc.getLongitude());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
