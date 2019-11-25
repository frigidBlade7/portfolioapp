package com.codedevtech.models;

import androidx.core.util.PatternsCompat;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginCredentialsTest {

    private LoginCredentials loginCredentials = new LoginCredentials();


    @Test
    public void isEmailValid() {

        loginCredentials.setEmail("kobbatt@gmail.com");
        assertTrue(PatternsCompat.EMAIL_ADDRESS.matcher(loginCredentials.getEmail()).matches());
    }
}