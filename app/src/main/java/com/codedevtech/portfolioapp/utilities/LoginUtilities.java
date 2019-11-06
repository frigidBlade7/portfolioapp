package com.codedevtech.portfolioapp.utilities;

import android.util.Patterns;

import java.util.regex.Pattern;

public class LoginUtilities {

    public static Boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
