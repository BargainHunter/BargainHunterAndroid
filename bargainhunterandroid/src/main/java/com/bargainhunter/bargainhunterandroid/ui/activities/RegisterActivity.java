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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bargainhunter.bargainhunterandroid.R;
import com.bargainhunter.bargainhunterandroid.models.User;

import java.util.regex.Pattern;


public class RegisterActivity extends ActionBarActivity {
    private String mEmail;
    private String PASSWORD;
    private View registerProgressView;
    private View registerFormView;
    private UserRegisterTask registerTask;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerProgressView =findViewById(R.id.registerProgressBar);
        registerFormView = findViewById(R.id.registerForm);
        Bundle extras = getIntent().getExtras();
        mEmail = extras.getString("email");
        email = (EditText) findViewById(R.id.emailEditText);
        email.setText(mEmail);
        final EditText passworEdit = (EditText) findViewById(R.id.passwordEditText);
        Button registerBtn = (Button) findViewById(R.id.createAccountButton);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
//                Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
//                intent.putExtra("password",passworEdit.getText().toString());
               // RegisterActivity.this.finish;
//                showProgress(true);

                    checkEmail(email.getText().toString());

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


        public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
                "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                        "\\@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
        );
    private boolean checkEmail(String email) {
        boolean res=false;
        res = EMAIL_ADDRESS_PATTERN.matcher(email).matches();
         if(res){
             showProgress(true);
             registerTask = new UserRegisterTask();
             registerTask.execute();
        }
        else {
             Toast.makeText(RegisterActivity.this,"wrong email",Toast.LENGTH_LONG).show();
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
        User user;

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
//                                EditText email = (EditText) findViewById(R.id.emailEditText);
//                                EditText password = (EditText) findViewById(R.id.passwordEditText);
//                                Intent intent = new Intent();
//                                intent.putExtra("email",email.getText().toString());
//                                intent.putExtra("password",password.getText().toString());
//                                setResult(RESULT_OK,intent);
                                finish();

//                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
               // finish();
            }
        }
    }


}
