package com.bargainhunter.bargainhunterandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.ui.activities.LoginActivity;
import com.bargainhunter.bargainhunterandroid.ui.activities.RegisterActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.util.Transcript;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static org.robolectric.RobolectricForMaps.shadowOf;

@Config(reportSdk = 18,emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class RegisterActivityTest {

    Activity loginActivity,registerActivity;
    RegisterActivity activity;
    EditText firstName,lastName, email, password;
    Button createAccount;
    @Before
    public void setUp() throws Exception {
       activity = Robolectric.buildActivity(RegisterActivity.class).create().get();
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().get();
        registerActivity = Robolectric.buildActivity(RegisterActivity.class).create().get();
        firstName = (EditText) activity.findViewById(R.id.firstNameEditText);
        lastName = (EditText) registerActivity.findViewById(R.id.lastNameEditText);
        email = (EditText) registerActivity.findViewById(R.id.emailEditText);
        password = (EditText) registerActivity.findViewById(R.id.passwordEditText);
        createAccount = (Button) activity.findViewById(R.id.createAccountButton);
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
        registerActivity = null;

    }


    @Test
    public void testIsEmailNotValid() throws Exception {
       //activity.onCreate(null);
        email.setText("vasovrk@gmail");
        createAccount.performClick();
        boolean cancel = activity.attemptRegister();
        assertThat(cancel,equalTo(true));


    }
}