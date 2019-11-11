package com.codedevtech.authenticationserviceprovider.interfaces;


import android.net.wifi.hotspot2.pps.Credential;

import com.codedevtech.authenticationserviceprovider.callbacks.AttemptLoginCallback;
import com.google.firebase.auth.AuthCredential;

public interface AuthenticationService {

    void attemptLogin(String username, String password, AttemptLoginCallback attemptLoginCallback);

    void attemptLoginWithCredential(AuthCredential authCredential, AttemptLoginCallback attemptLoginCallback);

}
