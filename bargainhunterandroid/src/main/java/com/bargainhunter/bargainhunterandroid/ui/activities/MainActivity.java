package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.ui.fragments.*;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        StoreListFragment.OnFragmentInteractionListener,
        StoreInfoFragment.OnFragmentInteractionListener,
        OfferListFragment.OnFragmentInteractionListener,
        OfferInfoFragment.OnFragmentInteractionListener,
        OfferListFromStoreFragment.OnFragmentInteractionListener,
        FilterDialogFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        int sectionNumber = position + 1;

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Change to Map fragment.
        Fragment fragment = OfferListFragment.newInstance(sectionNumber);
//        Fragment fragment =  MapFragment.newInstance(sectionNumber);

        switch (sectionNumber) {
            case 1:
//                fragment = MapFragment.newInstance(sectionNumber);
                break;
            case 2:
                fragment = OfferListFragment.newInstance(sectionNumber);
                break;
            case 3:
                fragment = StoreListFragment.newInstance(sectionNumber);
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStoreListFragmentInteraction(String storeId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment storeInfoFragment = StoreInfoFragment.newInstance(1, storeId);

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, storeInfoFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStoreInfoFragmentInteraction(Uri uri) {
    }

    @Override
    public void onOfferListFragmentInteraction(String offerId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment offerInfoFragment = OfferInfoFragment.newInstance(1, offerId);

        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, offerInfoFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onOfferInfoFragmentInteraction(Uri uri) {
    }

    @Override
    public void onOfferListFromStoreFragmentInteraction(String offerId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment offerInfoFragment = OfferInfoFragment.newInstance(2, offerId);
        fragmentManager.beginTransaction()
                .replace(R.id.mainContainer, offerInfoFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
