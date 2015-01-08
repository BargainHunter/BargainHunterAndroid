package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.entities.User;

import java.util.regex.Pattern;


public class RegisterActivity extends ActionBarActivity {
    private String mEmail;
    private String PASSWORD;
    private View registerProgressView;
    private View registerFormView;
    private UserRegisterTask registerTask;
    private EditText emailEditText;
    private EditText passwordEditText, firstNameEditText, lastNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerProgressView =findViewById(R.id.registerProgressBar);
        registerFormView = findViewById(R.id.registerForm);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            mEmail = extras.getString("email");
        }
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        emailEditText.setText(mEmail);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        Button registerBtn = (Button) findViewById(R.id.createAccountButton);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    attemptRegister();

            }
        }
    );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registerctivity, menu);
        return true;
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

    public boolean attemptRegister(){
        boolean cancel =false;
        firstNameEditText.setError(null);
        lastNameEditText.setError(null);
        emailEditText.setError(null);
        passwordEditText.setError(null);

        View focusView = null;
        if (!isEmailValid(emailEditText.getText().toString())){
            emailEditText.setError("Email invalid or empty");
            focusView = emailEditText;
            cancel = true;
        }

        if (!isValid(passwordEditText.getText().toString())){
            passwordEditText.setError("Password too short");
            focusView = passwordEditText;
            cancel = true;
        }


        if(!isValid(firstNameEditText.getText().toString())){
            firstNameEditText.setError("Firstname too short");
            focusView = firstNameEditText;
            cancel = true;
        }
        if(!isValid(lastNameEditText.getText().toString())){
            lastNameEditText.setError("Lastname too short");
            focusView = lastNameEditText;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
        }
        else {
            showProgress(true);
            registerTask = new UserRegisterTask();
            registerTask.execute();
        }
        return cancel;
    }

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    private boolean isEmailValid(String email){
        boolean res = false;
        if(EMAIL_ADDRESS_PATTERN.matcher(email).matches() && !TextUtils.isEmpty(email)){
            res = true;
        }
        return res;
    }

    private boolean isValid(String field){
        boolean res = false;
        if(field.length() > 3 && !TextUtils.isEmpty(field)){
            res = true;
        }
        return res;
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            registerFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            registerProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            registerProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    public class UserRegisterTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: implement connection with  server and store in database
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showProgress(false);
            if(aBoolean) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Registration Successfull! Please Login")
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Intent intent = new Intent(RegisterActivity.this,SplashScreen.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
               // finish();
            }
        }
    }


}
