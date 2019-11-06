package com.codedevtech.authenticationserviceprovider.interfaces;


import com.codedevtech.authenticationserviceprovider.callbacks.AttemptLoginCallback;

public interface AuthenticationService {

    void attemptLogin(String username, String password, AttemptLoginCallback attemptLoginCallback);

}
