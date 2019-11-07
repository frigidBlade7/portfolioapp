package com.codedevtech.authenticationserviceprovider.interface_implementations;

import android.util.Log;

import androidx.annotation.NonNull;

import com.codedevtech.authenticationserviceprovider.callbacks.AttemptLoginCallback;
import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
}
