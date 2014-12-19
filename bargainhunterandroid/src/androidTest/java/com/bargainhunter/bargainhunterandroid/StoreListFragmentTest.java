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

    public void testStoreListFirstStore() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(1);
    }

    public void testStoreListFirstAndSecondStore() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(1);
        solo.goBack();
        solo.clickInList(2);
    }

    public void testStoreListFirstStoreAndFirstOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(1);
        solo.clickInList(1);
    }

    public void testStoreListFirstStoreAndSecondOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(1);
        solo.clickInList(2);
    }

    public void testStoreListSecondStoreAndFirstOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(2);
        solo.clickInList(1);
    }

    public void testStoreListSecondStoreAndSecondOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(2);
        solo.clickInList(2);
    }

    public void testStoreListThirdStoreAndFirstOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(3);
        solo.clickInList(1);
    }

    public void testStoreListThirdStoreAndSecondOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(3);
        solo.clickInList(2);
    }

    public void testStoreListFourthStoreAndFirstOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(4);
        solo.clickInList(1);
    }

    public void testStoreListFourthStoreAndSecondOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(4);
        solo.clickInList(2);
    }

    public void testStoreListFifthStoreAndFirstOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(5);
        solo.clickInList(1);
    }

    public void testStoreListFifthStoreAndSecondOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(5);
        solo.clickInList(2);
    }

    public void testStoreListSixthStoreAndFirstOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(6);
        solo.clickInList(1);
    }

    public void testStoreListSixthStoreAndSecondOffer() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(6);
        solo.clickInList(2);
    }

        public void testStoreListFirstStoreOffers() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(1);
        solo.clickInList(1);
        solo.goBack();
        solo.clickInList(2);
    }

    public void testStoreListSecondStoreOffers() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(2);
        solo.clickInList(1);
        solo.goBack();
        solo.clickInList(2);
    }

    public void testStoreListThirdStoreOffers() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(3);
        solo.clickInList(1);
        solo.goBack();
        solo.clickInList(2);
    }

    public void testStoreListFourthStoreOffers() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(4);
        solo.clickInList(1);
        solo.goBack();
        solo.clickInList(2);
    }

    public void testStoreListFifthStoreOffers() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(5);
        solo.clickInList(1);
        solo.goBack();
        solo.clickInList(2);
    }

    public void testStoreListSixthStoreOffers() throws Exception {
        solo.assertCurrentActivity("check on first activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("Stores");
        solo.clickInList(6);
        solo.clickInList(1);
        solo.goBack();
        solo.clickInList(2);
    }





}
