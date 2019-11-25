package com.codedevtech.models;

import androidx.core.util.PatternsCompat;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class RegistrationCredentialsTest {

    final String PASSWORD_REGEX = "(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?!.*\\s)[0-9a-zA-Z*$-+?_&=!%{}/'.]*$";

    private RegistrationCredentials registrationCredentials = new RegistrationCredentials();

    @Test
    public void isEmailValid() {

        registrationCredentials.setEmail("kobbatt@gmail.com");
        assertTrue(PatternsCompat.EMAIL_ADDRESS.matcher(registrationCredentials.getEmail()).matches());
    }

    @Test
    public void isPasswordsMatch() {
        registrationCredentials.setPassword("january1");
        registrationCredentials.setPasswordConfirmation("january1");

        assertEquals(registrationCredentials.getPassword(), registrationCredentials.getPasswordConfirmation());
    }

    @Test
    public void isPasswordValid() {
        registrationCredentials.setPassword("january1");

        assertTrue(Pattern.matches(PASSWORD_REGEX, registrationCredentials.getPassword()));

        assertFalse(registrationCredentials.getPassword().isEmpty());
    }
}