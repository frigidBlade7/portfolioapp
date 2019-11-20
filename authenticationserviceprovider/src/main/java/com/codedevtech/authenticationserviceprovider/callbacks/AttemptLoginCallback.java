package com.codedevtech.authenticationserviceprovider.callbacks;

public interface AttemptLoginCallback {

    //to be called when login fails
    void onAttemptLoginFailed(String errorMessage);

    //to be called when we successfully login
    void onAttemptLoginSuccess(String userId);

    //sigh do i need this? maybe
    void onErrorOccurred(String errorMessage);

}
