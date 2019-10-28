package com.codedevtech.authenticationserviceprovider.callbacks;

public interface AttemptLoginCallback {

    void onAttemptLoginFailed(String errorMessage);

    void onAttemptLoginSuccess();

    void onErrorOccurred(String errorMessage);

}
