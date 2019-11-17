package com.codedevtech.models;

import android.util.Patterns;

import com.codedevtech.Utilities.LoginUtilities;

import java.util.regex.Pattern;

public class RegistrationCredentials {

    //todo confirm this works
    final String PASSWORD_REGEX = "(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?!.*\\s)[0-9a-zA-Z*$-+?_&=!%{}/'.]*$";

    private String email;
    private String password;
    private String passwordConfirmation;


    public RegistrationCredentials() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }


    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    //email must not be null and email must be valid
    public boolean isEmailValid(){
        return (email!=null &&
                !email.isEmpty() &&
                LoginUtilities.isEmailValid(email)
        );
    }

    public boolean isPasswordsMatch(){
        return (password!= null &&
                !password.isEmpty() &&
                password.equals(passwordConfirmation)
        );
    }

    public boolean isPasswordValid(){
        return (password!=null
                && !password.isEmpty() && Pattern.compile(PASSWORD_REGEX).matcher(password).matches()
        );
    }
}
