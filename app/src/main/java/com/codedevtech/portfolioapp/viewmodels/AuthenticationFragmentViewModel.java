package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.utilities.LoginUtilities;

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

    public void goToAuthenticationExtras(View view){
        setDestinationId(R.id.action_authenticationFragment_to_authenticationExtrasBottomSheet);
    }

    public void goToAuthenticationExtrasAlternate(View view){
        setDestinationId(R.id.action_authenticationFragment_to_authenticationExtrasBottomSheetAlternate);
    }

    public void goToCompleteProfileFederated(View view){
        setDestinationId(R.id.action_authenticationExtrasBottomSheet_to_completeProfileFragment);
    }

    public void goToCompleteProfile(View view){
        setDestinationId(R.id.action_authenticationFragment_to_completeProfileFragment);
    }

    public void goToForgotPassword(View view){
        setDestinationId(R.id.action_authenticationFragment_to_forgotPasswordFragment);
    }

    public void goToDashboard(View view){
        setDestinationId(R.id.action_authenticationFragment_to_dashboardFragment);
    }




    public void attemptLogin(View view){
        String passwordString = passwordMutableData.getValue(), emailString = emailMutableLiveData.getValue();

        Log.d(TAG, "attemptLogin: "+passwordString+ emailString);

    }
}
