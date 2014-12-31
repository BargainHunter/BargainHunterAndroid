package com.bargainhunter.bargainhunterandroid;

import android.app.Activity;
import com.bargainhunter.bargainhunterandroid.ui.activities.LoginActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

/**
 * Created by vasovourka on 12/31/14.
 */
@Config(emulateSdk=18)
@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {

//    @Test
//    public void testMainActivityNotNull(){
//        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();
//
//        Button pressMeButton = (Button) activity.findViewById(R.id.button);
//        TextView results = (TextView) activity.findViewById(R.id.textView);
//
//        pressMeButton.performClick();
//        String resultsText = results.getText().toString();
//        assertNotNull(activity);
    //  assertThat(resultsText, equalTo("Testing Android Rocks!"));
    //   assertThat(resultsText,equalTo(" "));
//        ShadowActivity shadowActivity = shadowOf(activity);
//
//        pressMeButton.performClick();
//
//        Intent startedIntent = shadowActivity.getNextStartedActivity();
//        assertThat(startedIntent.getComponent().getClassName(),equalTo(LoginActivity.class.getName()));
//        LoginActivity activity= Robolectric.buildActivity(LoginActivity.class).create().visible().get();
//        EditText password = (EditText) activity.findViewById(R.id.password);
//        ShadowTextView shadowTextView = Robolectric.shadowOf(password);
//        password.setText("123");
//        int res = password.length();
//        assertEquals(4,res);
    //}
//    @Test
//    public void test2(){
//        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();
//        Button button = (Button) activity.findViewById(R.id.button);
//        ShadowActivity shadowActivity = shadowOf(activity);
//
//        button.performClick();
//
//        Intent startedIntent = shadowActivity.getNextStartedActivity();
//        assertThat(startedIntent.getComponent().getClassName(),
//                equalTo(LoginActivity.class.getName()));
//    }

    @Test
    public void testNextActivityCalled(){
        Activity activity = Robolectric.buildActivity(LoginActivity.class).create().get();
        assertNotNull(activity);
    }
//    @Test
//    public void testLoginActivityNotNull(){
//        Activity activity = Robolectric.buildActivity(LoginActivity.class).create().get();
////        TextView textView = (TextView) activity.findViewById(R.id.textView);
////        assertNotNull(textView.getText().toString());
//        assertNotNull("activity not used",activity);
//
//    }
//
//    @Test
//    public void testEmailAtLeastFour(){
//        Activity activity = Robolectric.buildActivity(LoginActivity.class).create().get();
//        AutoCompleteTextView email = (AutoCompleteTextView) activity.findViewById(R.id.email);
//        email.setText("lala");
//        int res = email.length();
//        assertThat(res,greaterThanOrEqualTo(4));
//    }
//
//    @Test
//    public void testEmailContainsSpecifiedChar(){
//        Activity activity = Robolectric.buildActivity(LoginActivity.class).create().get();
//        AutoCompleteTextView email = (AutoCompleteTextView) activity.findViewById(R.id.email);
//        email.setText("lalala@gmail.com");
//        assertThat(email.getText().toString(),containsString("@"));
//    }
//
//    @Test
//    public void testPasswordNotNullWhenAttemptLogin(){
//        Activity activity = Robolectric.buildActivity(LoginActivity.class).create().get();
//        EditText password = (EditText) activity.findViewById(R.id.password);
//        AutoCompleteTextView email = (AutoCompleteTextView) activity.findViewById(R.id.email);
//        email.setText("lalal@gmail.com");
//        Button signInBtn = (Button) activity.findViewById(R.id.email_sign_in_button);
//        password.setText("12345");
//        signInBtn.performClick();
//        assertThat(password.getText().toString(),not(equalTo("")));
//    }
//
//    @Test
//    public void testEmailNotNullWhenAttemptLogin(){
//        Activity activity = Robolectric.buildActivity(LoginActivity.class).create().get();
//        EditText password = (EditText) activity.findViewById(R.id.password);
//        AutoCompleteTextView email = (AutoCompleteTextView) activity.findViewById(R.id.email);
//
//    }
}
