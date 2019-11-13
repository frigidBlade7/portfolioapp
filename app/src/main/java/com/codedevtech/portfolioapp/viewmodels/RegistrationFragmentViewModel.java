package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;
import com.codedevtech.portfolioapp.R;

import javax.inject.Inject;

public class RegistrationFragmentViewModel extends BaseViewModel {

    private MutableLiveData<String> passwordMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> passwordConfirmationMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> emailLiveData = new MutableLiveData<>();
    private AuthenticationService authenticationService;


    @Inject
    public RegistrationFragmentViewModel(@NonNull Application application, AuthenticationService authenticationService) {
        super(application);
        this.authenticationService = authenticationService;
    }


    public void goToCompleteProfile(View view){
        setDestinationId(R.id.action_registrationFragment_to_completeProfileFragment);
    }

    public MutableLiveData<String> getPasswordMutableLiveData() {
        return passwordMutableLiveData;
    }

    public void setPasswordMutableLiveData(String password) {
        this.passwordMutableLiveData.setValue(password);
    }

    public MutableLiveData<String> getPasswordConfirmationMutableLiveData() {
        return passwordConfirmationMutableLiveData;
    }

    public void setPasswordConfirmationMutableLiveData(String passwordConfirmation) {
        this.passwordConfirmationMutableLiveData.setValue(passwordConfirmation);
    }

    public MutableLiveData<String> getEmailLiveData() {
        return emailLiveData;
    }

    public void setEmailLiveData(String email) {
        this.emailLiveData.setValue(email);
    }
}
