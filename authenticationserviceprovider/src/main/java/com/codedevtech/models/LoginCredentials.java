package com.codedevtech.models;

import com.codedevtech.Utilities.LoginUtilities;

public class LoginCredentials {

    private String password;
    private String email;
    //for future use maybe?
    private String phoneNumber;
    private String userName;

    public LoginCredentials() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    //email must not be null and email must be valid
    public boolean isEmailValid(){
        return (email!=null && LoginUtilities.isEmailValid(email));
    }
}
