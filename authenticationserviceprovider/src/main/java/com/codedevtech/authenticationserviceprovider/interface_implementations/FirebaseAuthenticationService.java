package com.codedevtech.authenticationserviceprovider.interface_implementations;

import android.net.wifi.hotspot2.pps.Credential;
import android.util.Log;

import androidx.annotation.NonNull;

import com.codedevtech.authenticationserviceprovider.callbacks.AttemptLoginCallback;
import com.codedevtech.authenticationserviceprovider.callbacks.AttemptRegistrationCallback;
import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthenticationService implements AuthenticationService {

    private static final String TAG = "FirebaseAuthenticationS";
    private FirebaseAuth firebaseAuthInstance;

    public FirebaseAuthenticationService() {
        firebaseAuthInstance = FirebaseAuth.getInstance();
    }

    @Override
    public void attemptLogin(String username, String password, final AttemptLoginCallback attemptLoginCallback) {
        firebaseAuthInstance.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    attemptLoginCallback.onAttemptLoginSuccess();
                }else{
/*                    if(task.isCanceled())
                        attemptLoginCallback.onErrorOccurred(task.getException().getLocalizedMessage());*/
                    Log.d(TAG, "onComplete: "+task.getException().getLocalizedMessage());

                    attemptLoginCallback.onAttemptLoginFailed(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void attemptLoginWithCredential(AuthCredential authCredential,final AttemptLoginCallback attemptLoginCallback) {
        firebaseAuthInstance.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    attemptLoginCallback.onAttemptLoginSuccess();
                }else{
/*                    if(task.isCanceled())
                        attemptLoginCallback.onErrorOccurred(task.getException().getLocalizedMessage());*/
                    Log.d(TAG, "onComplete: "+task.getException().getLocalizedMessage());

                    attemptLoginCallback.onAttemptLoginFailed(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void attemptRegistrationWithCredential(String username, String password, final AttemptRegistrationCallback attemptRegistrationCallback) {
        firebaseAuthInstance.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    attemptRegistrationCallback.onAttemptRegistrationSuccess();
                }else{

                    Log.d(TAG, "onComplete: "+task.getException().getLocalizedMessage());

                    attemptRegistrationCallback.onAttemptRegistrationFailed(task.getException().getLocalizedMessage());
                }
            }
        });
    }
}
