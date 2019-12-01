package com.codedevtech.authenticationserviceprovider.callbacks;

public interface AttemptRegistrationCallback {

    //to be called when registration fails
    void onAttemptRegistrationFailed(String errorMessage);

    //to be called when we successfully register a user
    void onAttemptRegistrationSuccess();

    //sigh do i need this? maybe
    void onErrorOccurred(String errorMessage);

}
