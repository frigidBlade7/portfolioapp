package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.Intent;
import android.telecom.Call;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.authenticationserviceprovider.callbacks.AttemptLoginCallback;
import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;
import com.codedevtech.models.LoginCredentials;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.navigation.Event;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import static com.codedevtech.portfolioapp.utilities.RequestCodeUtilities.RC_SIGN_IN;

public class AuthenticationFragmentViewModel extends BaseViewModel {

    private static final String TAG = "AuthenticationFragmentV";
    private AuthenticationService authenticationService;
    private CallbackManager callbackManager;

    private MutableLiveData<String> emailMutableLiveData = new MutableLiveData<>(), passwordMutableData = new MutableLiveData<>();
    private MutableLiveData<Event<String>> facebookLoginParameter = new MutableLiveData<>();
    private MutableLiveData<Event<Intent>> signInIntent = new MutableLiveData<>();
    private GoogleSignInClient googleSignInClient;

    @Inject
    public AuthenticationFragmentViewModel(@NonNull Application application, AuthenticationService authenticationService,
                                           GoogleSignInClient googleSignInClient, CallbackManager callbackManager) {
        super(application);
        this.authenticationService = authenticationService;
        this.googleSignInClient = googleSignInClient;
        this.callbackManager = callbackManager;

        //initialise facebook login  callback manager
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        goToDashboardFromAuthExtras();
                        Log.d(TAG, "success: ");

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d(TAG, "onCancel: ");
                        setSnackbarMessageUsingId(R.string.failed_to_sign_in_with_your_facebook_account);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d(TAG, "onError: "+exception.getLocalizedMessage());
                        setSnackbarMessage(exception.getLocalizedMessage());

                    }
                });


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


    public void attemptGoogleSignIn(View view){
        setDestinationId(0);

        setSignInIntent(googleSignInClient.getSignInIntent());
    }

    public void setFacebookLoginParameter(String facebookLoginParameter) {
        this.facebookLoginParameter.setValue(new Event<String>(facebookLoginParameter));
    }

    public MutableLiveData<Event<String>> getFacebookLoginParameter() {
        return facebookLoginParameter;
    }

    public void attemptFacebookSignIn(View view){
        setDestinationId(0);

        setFacebookLoginParameter("email");
    }


    //show authentication extras 
    public void goToAuthenticationExtras(View view){
        setDestinationId(R.id.action_authenticationFragment_to_authenticationExtrasBottomSheet);
    }

    public void goToManualRegistration(View view){
        setDestinationId(R.id.action_authenticationExtrasBottomSheet_to_registrationFragment);
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


    //navigate to dashboard fragment
    public void goToDashboardFromAuthExtras(){
        setDestinationId(R.id.action_authenticationExtrasBottomSheet_to_dashboardNavigation);
    }

    public MutableLiveData<Event<Intent>> getSignInIntent() {
        return signInIntent;
    }

    public void setSignInIntent(Intent signInIntent) {
        this.signInIntent.setValue(new Event<Intent>(signInIntent));
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

    public void executeOnActivityResultCalled(int requestCode, int resultCode, Intent data) {

        if(requestCode == RC_SIGN_IN){
            //google
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                // Signed in successfully, show authenticated UI.
                goToDashboardFromAuthExtras();
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                setSnackbarMessageUsingId(R.string.failed_to_sign_in_with_your_google_account);
            }

        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }


    }

}
