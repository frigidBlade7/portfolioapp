package com.codedevtech.portfolioapp.models;

import androidx.core.util.PatternsCompat;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class FolioUserTest {

    private FolioUser folioUser = new FolioUser();

    @Test
    public void isIdValid() {
        //
        folioUser.setId("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3" +
                "ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ." +
                "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

        assertTrue(!folioUser.getId().isEmpty());
    }

    @Test
    public void isEmailValid() {
        folioUser.setEmail("kobbatt@gmail.com");
        assertTrue(PatternsCompat.EMAIL_ADDRESS.matcher(folioUser.getEmail()).matches());

    }

    @Test
    public void isRoleFlagsValid() {
        folioUser.setRoleFlags(Arrays.asList("Designer","Developer"));
        assertTrue(folioUser.getRoleFlags().size()>1);
    }
}