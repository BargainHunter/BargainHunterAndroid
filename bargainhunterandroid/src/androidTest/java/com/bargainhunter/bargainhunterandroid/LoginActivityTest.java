package com.bargainhunter.bargainhunterandroid;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import com.bargainhunter.bargainhunterandroid.ui.activities.LoginActivity;
import com.bargainhunter.bargainhunterandroid.ui.activities.PlusBaseActivity;
import com.bargainhunter.bargainhunterandroid.ui.activities.SplashScreen;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by vasovourka on 12/31/14.
 */
@Config(emulateSdk=18, reportSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {
    Activity loginActivity;
    SplashScreen splashScreen;
    @Before
    public void setUp() throws Exception {
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().get();

    }

    @After
    public void tearDown() throws Exception {
        loginActivity=null;

    }

    @Test
    public void testLoginActivityCreated(){
        assertNotNull(loginActivity);
    }

    @Test
    public void testNextActivityIsCalled(){
        Button signInBtn = (Button) loginActivity.findViewById(R.id.email_sign_in_button);
        ShadowActivity shadowActivity = shadowOf(loginActivity);
        signInBtn.performClick();
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertThat(startedIntent.getComponent().getClassName(),equalTo(SplashScreen.class.getName()));
    }

    @Test
    public void testEmailAtLeastFour(){
        AutoCompleteTextView email = (AutoCompleteTextView) loginActivity.findViewById(R.id.email);
        email.setText("lalaaa");
        int res = email.length();
        assertThat(res,greaterThan(4));
    }

    @Test
    public void testEmailContainsAtCharacter(){
        AutoCompleteTextView email = (AutoCompleteTextView) loginActivity.findViewById(R.id.email);
        email.setText("lalala@gmail.com");
        MatcherAssert.assertThat(email.getText().toString(), containsString("@"));
    }

    @Test
    public void testPassWordNotNullWhenAttemptLogin(){
        EditText password = (EditText) loginActivity.findViewById(R.id.password);
        AutoCompleteTextView email = (AutoCompleteTextView) loginActivity.findViewById(R.id.email);
        email.setText("lalal@gmail.com");
        Button signInBtn = (Button) loginActivity.findViewById(R.id.email_sign_in_button);
        password.setText("12345");
        signInBtn.performClick();
        MatcherAssert.assertThat(password.getText().toString(), not(equalTo("")));
    }

//    @Test
//    public void testEmailNotNullWhenAttemptLogin(){
//        EditText password = (EditText) loginActivity.findViewById(R.id.password);
//        AutoCompleteTextView email = (AutoCompleteTextView) loginActivity.findViewById(R.id.email);
//    }
}
