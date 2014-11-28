package com.bargainhunter.bargainhunterandroid;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        StoreListFragment.OnFragmentInteractionListener,
        StoreInfoFragment.OnFragmentInteractionListener,
        OfferListFragment.OnFragmentInteractionListener,
        OfferInfoFragment.OnFragmentInteractionListener,
        OfferListFromStoreFragment.OnFragmentInteractionListener,
        StoreFragment.OnFragmentInteractionListener {

    private static final String ENDPOINT = "http://bargainhunter.dyndns.org:8080/bargainhunterws";

    /**
     * Represents radius in (km). //TODO: change the value from settings
     */
    private static final double RADIUS = 100.0;

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
        Fragment fragment = OfferListFragment.newInstance(sectionNumber, ENDPOINT, RADIUS);
//        Fragment fragment = StoreInfoFragment.newInstance(sectionNumber, "1", ENDPOINT);
//        Fragment fragment = OfferInfoFragment.newInstance(sectionNumber, "1", ENDPOINT);
//        Fragment fragment =  MapFragment.newInstance(sectionNumber);

        switch (sectionNumber) {
            case 1:
//                fragment = MapFragment.newInstance(sectionNumber);
                break;
            case 2:
                fragment = OfferListFragment.newInstance(sectionNumber, ENDPOINT, RADIUS);
                break;
            case 3:
                fragment = StoreListFragment.newInstance(sectionNumber, ENDPOINT, RADIUS);
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStoreListFragmentInteraction(String id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment storeFragment = StoreFragment.newInstance(ENDPOINT, 1, id, RADIUS);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_store_info_offer_list, storeFragment)
                .commit();
    }

    @Override
    public void onStoreInfoFragmentInteraction(Uri uri) {
    }

    @Override
    public void onOfferListFragmentInteraction(String id) {
    }

    @Override
    public void onOfferInfoFragmentInteraction(Uri uri) {
    }

    @Override
    public void onOfferListFromStoreFragmentInteraction(String id) {
    }

    @Override
    public void onFragmentInteraction(String endpoint,
                                      int sectionNumber,
                                      String storeId,
                                      double radius) {


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment storeInfoFragment = StoreInfoFragment.newInstance(sectionNumber, storeId, endpoint);
        Fragment offerListFromStoreFragment = OfferListFromStoreFragment.newInstance(sectionNumber, endpoint, storeId);

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_store_container, storeInfoFragment)
                .replace(R.id.fragment_offer_list_from_store_container, offerListFromStoreFragment)
                .commit();

    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//
//        @Override
//        public void onAttach(Activity activity) {
//            super.onAttach(activity);
//            ((MainActivity) activity).onSectionAttached(
//                    getArguments().getInt(ARG_SECTION_NUMBER));
//        }
//    }
}