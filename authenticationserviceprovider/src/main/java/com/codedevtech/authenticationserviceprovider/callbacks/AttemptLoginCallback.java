package com.codedevtech.authenticationserviceprovider.callbacks;

public interface AttemptLoginCallback {

    void onAttemptLoginFailed();

    void onAttemptLoginSuccess();

    void onErrorOccured();

}
