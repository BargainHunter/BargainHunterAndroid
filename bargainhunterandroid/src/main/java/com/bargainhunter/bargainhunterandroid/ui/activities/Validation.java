package com.bargainhunter.bargainhunterandroid.ui.activities;

import android.text.TextUtils;
import com.bargainhunter.bargainhunterandroid.models.User;

import java.util.regex.Pattern;

/**
 * Created by vasovourka on 1/4/15.
 */
public class Validation {
    private User user;
    private String email = null, firstName = null, lastName = null, username = null, password = null;

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public Validation(User user) {
        this.user = user;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public boolean checkForValidOrEmptyEmail() {
        boolean cancel = false;
        if (!EMAIL_ADDRESS_PATTERN.matcher(user.getEmail()).matches() || TextUtils.isEmpty(user.getEmail())) {
//            return "invalid or empty email";

        }

        if(password.length()>5 || TextUtils.isEmpty(password)){

        }

        if(username.length()>5 || TextUtils.isEmpty(username)){

        }

        if(firstName.length()>5 || TextUtils.isEmpty(firstName)){

        }

        if(lastName.length()>5 || TextUtils.isEmpty(lastName)){

        }
        return cancel;
    }




}
