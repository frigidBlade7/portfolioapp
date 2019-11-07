package com.codedevtech.Utilities;

import android.util.Patterns;

public class LoginUtilities {

    public static Boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
