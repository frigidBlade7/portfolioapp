package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.authenticationserviceprovider.callbacks.AttemptLoginCallback;
import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;
import com.codedevtech.models.LoginCredentials;
import com.codedevtech.portfolioapp.R;

import javax.inject.Inject;

public class AuthenticationFragmentViewModel extends BaseViewModel {

    private static final String TAG = "AuthenticationFragmentV";
    private AuthenticationService authenticationService;

    private MutableLiveData<String> emailMutableLiveData = new MutableLiveData<>(), passwordMutableData = new MutableLiveData<>();

    @Inject
    public AuthenticationFragmentViewModel(@NonNull Application application, AuthenticationService authenticationService) {
        super(application);
        this.authenticationService = authenticationService;
    }

    public MutableLiveData<String> getEmailMutableLiveData() {
        if(emailMutableLiveData == null)
            return new MutableLiveData<>();
        return emailMutableLiveData;
    }

    public MutableLiveData<String> getPasswordMutableData() {
        if(passwordMutableData == null)
            return new MutableLiveData<>();

        return passwordMutableData;
    }

    //show authentication extras 
    public void goToAuthenticationExtras(View view){
        setDestinationId(R.id.action_authenticationFragment_to_authenticationExtrasBottomSheet);
    }

    //show authentication extras, but with a different view
    public void goToAuthenticationExtrasAlternate(View view){
        setDestinationId(R.id.action_authenticationFragment_to_authenticationExtrasBottomSheetAlternate);
    }

    //navigate to completeprofile fragment from federated identity auth
    public void goToCompleteProfileFederated(View view){
        setDestinationId(R.id.action_authenticationExtrasBottomSheet_to_completeProfileFragment);
    }

    //navigate to complete profile fragment

    public void goToCompleteProfile(View view){
        setDestinationId(R.id.action_authenticationFragment_to_completeProfileFragment);
    }

    //navigate to forgotpassword fragment
    public void goToForgotPassword(View view){
        setDestinationId(R.id.action_authenticationFragment_to_forgotPasswordFragment);
    }

    //navigate to dashboard fragment
    public void goToDashboard(){
        setDestinationId(R.id.action_authenticationFragment_to_dashboardFragment);
    }




    public void attemptLogin(View view){
        //show loader
        setDestinationId(R.id.loadingDialog);

        String passwordString = passwordMutableData.getValue(), emailString = emailMutableLiveData.getValue();
        //create new LoginCredentials object, set parameters
        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setEmail(emailString);
        loginCredentials.setPassword(passwordString);
        
        if(!loginCredentials.isEmailValid())
            setSnackbarMessageUsingId(R.string.invalid_credentials);
        else{
            authenticationService.attemptLogin(loginCredentials.getEmail(), loginCredentials.getPassword(),
                    new AttemptLoginCallback() {
                        @Override
                        public void onAttemptLoginFailed(String errorMessage) {
                            //hide loader
                            setDestinationId(0);

                            Log.d(TAG, "onAttemptLoginFailed: "+errorMessage);

                            //a little hack to prevent malicious users from experimenting with emails
                            if(errorMessage.contains("user"))
                                setSnackbarMessageUsingId(R.string.invalid_credentials);
                            else
                                setSnackbarMessage(errorMessage);

                        }

                        @Override
                        public void onAttemptLoginSuccess() {
                            //hide loader
                            setDestinationId(0);

                            goToDashboard();
                        }

                        @Override
                        public void onErrorOccurred(String errorMessage) {
                            Log.d(TAG, "onErrorOccurred: "+ errorMessage);
                        }
                    });
        }


    }
}
