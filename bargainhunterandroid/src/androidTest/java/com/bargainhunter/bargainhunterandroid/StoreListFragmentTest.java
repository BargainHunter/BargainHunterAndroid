package com.bargainhunter.bargainhunterandroid;


import android.test.ActivityInstrumentationTestCase2;
import com.bargainhunter.bargainhunterandroid.ui.activities.MainActivity;
import com.robotium.solo.Solo;


/**
 * Created by Vasilis on 9/12/2014.
 */
public class StoreListFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {


    private Solo solo;

    public StoreListFragmentTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStoreList() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
    }

    public void testOfferList() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Offers");
    }

    public void testMap() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Map");
    }

    public void testOfferListThenStoreList() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Offers");
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
    }

    public void testStoreListThenOfferList() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickOnImageButton(0);
        solo.clickOnText("Offers");
    }
}
