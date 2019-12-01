package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.authenticationserviceprovider.callbacks.AttemptLoginCallback;
import com.codedevtech.authenticationserviceprovider.callbacks.AttemptRegistrationCallback;
import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;
import com.codedevtech.models.RegistrationCredentials;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.commands.SnackbarCommand;
import com.codedevtech.portfolioapp.fragments.RegistrationFragmentDirections;

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


    public void goToCompleteProfile(String userId) {
        RegistrationFragmentDirections.ActionRegistrationFragmentToCompleteProfileFragment action =
                RegistrationFragmentDirections.actionRegistrationFragmentToCompleteProfileFragment(userId);

        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationAction(action));
        //setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_registrationFragment_to_completeProfileFragment));
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

    public void attemptRegistration() {


        RegistrationCredentials registrationCredentials = new RegistrationCredentials();
        registrationCredentials.setEmail(emailLiveData.getValue());
        registrationCredentials.setPassword(passwordMutableLiveData.getValue());
        registrationCredentials.setPasswordConfirmation(passwordConfirmationMutableLiveData.getValue());

        if(!registrationCredentials.isEmailValid()){
            setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.email_invalid));
        }
        else if(!registrationCredentials.isPasswordValid()){
            setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.error_invalid_password_format));
        }
        else if(!registrationCredentials.isPasswordsMatch()){
            setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.error_password_match));

        }else{

            setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.loadingDialog));

            authenticationService.attemptRegistrationWithCredential(registrationCredentials.getEmail(), registrationCredentials.getPassword(), new AttemptLoginCallback() {
                @Override
                public void onAttemptLoginFailed(String errorMessage) {

                    setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
                    setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(errorMessage));
                }

                @Override
                public void onAttemptLoginSuccess(String userId) {
                    setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
                    goToCompleteProfile(userId);

                }

                @Override
                public void onErrorOccurred(String errorMessage) {
                    //prolly gonna do same as the failed block
                }
            });
        }



    }
}