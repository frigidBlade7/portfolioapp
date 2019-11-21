package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.authenticationserviceprovider.callbacks.AttemptLoginCallback;
import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;
import com.codedevtech.models.LoginCredentials;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.callbacks.UserExistsCallback;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.fragments.AuthenticationExtrasBottomSheetDirections;
import com.codedevtech.portfolioapp.fragments.AuthenticationFragmentDirections;
import com.codedevtech.portfolioapp.interfaces.RegistrationService;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.commands.SnackbarCommand;
import com.codedevtech.portfolioapp.navigation.Event;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import static com.codedevtech.portfolioapp.utilities.RequestCodeUtilities.RC_SIGN_IN;

public class AuthenticationFragmentViewModel extends BaseViewModel {

    private static final String TAG = "AuthenticationFragmentV";
    private AuthenticationService authenticationService;
    private CallbackManager callbackManager;
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private Moshi moshi;
    private MutableLiveData<String> emailMutableLiveData = new MutableLiveData<>(), passwordMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Event<List<String>>> facebookLoginParameter = new MutableLiveData<>();
    private MutableLiveData<Event<Intent>> signInIntent = new MutableLiveData<>();
    private MutableLiveData<Event<OAuthProvider>> oAuthProviderLiveData = new MutableLiveData<>();
    private OAuthProvider oAuthProvider;
    private GoogleSignInClient googleSignInClient;
    private RegistrationService registrationService;

    @Inject
    public AuthenticationFragmentViewModel(@NonNull Application application, AuthenticationService authenticationService,
                                           GoogleSignInClient googleSignInClient, CallbackManager callbackManager,
                                           FirebaseRemoteConfig firebaseRemoteConfig, Moshi moshi, OAuthProvider oAuthProvider,
                                           RegistrationService registrationService) {
        super(application);
        this.authenticationService = authenticationService;
        this.googleSignInClient = googleSignInClient;
        this.callbackManager = callbackManager;
        this.firebaseRemoteConfig = firebaseRemoteConfig;
        this.moshi = moshi;
        this.oAuthProvider = oAuthProvider;
        this.registrationService = registrationService;

        //initialise facebook login  callback manager
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        //goToDashboardFromAuthExtras();
                        validateAuthResultOnSuccessfulLogin(FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken()));

                        Log.d(TAG, "success: ");

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d(TAG, "onCancel: ");
                        setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.failed_to_sign_in_facebook));
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d(TAG, "onError: "+exception.getLocalizedMessage());
                        setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(exception.getLocalizedMessage()));

                    }
                });


    }

    public MutableLiveData<Event<OAuthProvider>> getoAuthProvider() {
        return oAuthProviderLiveData;
    }

    public void setoAuthProvider(OAuthProvider oAuthProvider) {
        this.oAuthProviderLiveData.setValue(new Event<OAuthProvider>(oAuthProvider));
    }

    public MutableLiveData<String> getEmailMutableLiveData() {
        if(emailMutableLiveData == null)
            return new MutableLiveData<>();
        return emailMutableLiveData;
    }

    public MutableLiveData<String> getPasswordMutableLiveData() {
        if(passwordMutableLiveData == null)
            return new MutableLiveData<>();

        return passwordMutableLiveData;
    }


    public void attemptGoogleSignIn(View view){
        setSignInIntent(googleSignInClient.getSignInIntent());
    }

    public void setFacebookLoginParameter(List<String> facebookLoginParameter) {
        this.facebookLoginParameter.setValue(new Event<List<String>>(facebookLoginParameter));
    }

    public MutableLiveData<Event<List<String>>> getFacebookLoginParameter() {
        return facebookLoginParameter;
    }


    public void attemptTwitterSignIn(View view){
        Task<AuthResult> pendingTask = FirebaseAuth.getInstance().getPendingAuthResult();
        if(pendingTask!=null){
            //sign in flow is pending
            pendingTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //todo twitter login successful
                    }else{
                        //todo couldnt complete twitter login
                    }
                }
            });
        }else{
            //start sign in flow
            setoAuthProvider(oAuthProvider);

        }
    }

    public void attemptFacebookSignIn(View view){

        //use remote config to obtain facebook
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if(task.isSuccessful()){

                    //use moshi to convert json string returned from remote config to array
                    Type type = Types.newParameterizedType(List.class, String.class);
                    JsonAdapter<List<String>> adapter = moshi.adapter(type);
                    try {

                        //set as params list, try catch ensures the json string is appropriately formatted
                        List<String> facebookParamsList = adapter.fromJson(firebaseRemoteConfig.getString("facebook_login_sdk_params"));

                        Log.d(TAG, "onComplete: "+facebookParamsList);
                        setFacebookLoginParameter(facebookParamsList);

                    } catch (IOException e) {
                        Log.d(TAG, "onComplete: "+e.getLocalizedMessage());
                        setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.failed_to_sign_in_facebook));
                    }
                }else{
                    Log.d(TAG, "onComplete: "+task.getException().getLocalizedMessage());
                    setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.failed_to_sign_in_facebook));
                }
            }
        });
    }


    //show authentication extras 
    public void goToAuthenticationExtras(View view){
        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_authenticationFragment_to_authenticationExtrasBottomSheet));
    }

    public void goToManualRegistration(View view){
        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_authenticationExtrasBottomSheet_to_registrationFragment));
    }

    //show authentication extras, but with a different view
    public void goToAuthenticationExtrasAlternate(View view){
        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_authenticationFragment_to_authenticationExtrasBottomSheetAlternate));
    }

    //navigate to completeprofile fragment from federated identity auth
    public void goToCompleteProfileFederated(String userId){

        AuthenticationExtrasBottomSheetDirections.ActionAuthenticationExtrasBottomSheetToCompleteProfileFragment action =
                AuthenticationExtrasBottomSheetDirections.actionAuthenticationExtrasBottomSheetToCompleteProfileFragment(userId);
        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationAction(action));
    }
    //navigate to complete profile fragment

    public void goToCompleteProfile(String userId){

        //todo AuthenticationFragmentDirections authenticationFragmentDirections

        AuthenticationFragmentDirections.ActionAuthenticationFragmentToCompleteProfileFragment action =
                AuthenticationFragmentDirections.actionAuthenticationFragmentToCompleteProfileFragment(userId);

        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationAction(action));
        //setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_authenticationFragment_to_completeProfileFragment));
    }


    //navigate to forgotpassword fragment
    public void goToForgotPassword(View view){
        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_authenticationFragment_to_forgotPasswordFragment));
    }

    //navigate to dashboard fragment
    public void goToDashboard(String userAuthProviderId){

        registrationService.userExists(userAuthProviderId, new UserExistsCallback() {
            @Override
            public void userExists(FolioUser folioUser) {
                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_authenticationFragment_to_dashboardFragment));
            }

            @Override
            public void userDoesNotExist(String userId) {
                goToCompleteProfile(userId);
            }

            @Override
            public void error(String message) {
                setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(message));
            }
        });
    }


    //navigate to dashboard fragment
    public void goToDashboardFromAuthExtras(String userAuthProviderId){
        registrationService.userExists(userAuthProviderId, new UserExistsCallback() {
            @Override
            public void userExists(FolioUser folioUser) {
                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_authenticationExtrasBottomSheet_to_dashboardNavigation));
            }

            @Override
            public void userDoesNotExist(String userId) {
                goToCompleteProfileFederated(userId);
            }

            @Override
            public void error(String message) {
                setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(message));
            }
        });

    }

    public MutableLiveData<Event<Intent>> getSignInIntent() {
        return signInIntent;
    }

    public void setSignInIntent(Intent signInIntent) {
        this.signInIntent.setValue(new Event<Intent>(signInIntent));
    }

    public void attemptLogin(View view){


        String passwordString = passwordMutableLiveData.getValue(), emailString = emailMutableLiveData.getValue();

        if(emailString != null && passwordString != null && !emailString.isEmpty() && !passwordString.isEmpty()) {
            //show loader
            setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.loadingDialog));


            //create new LoginCredentials object, set parameters
            LoginCredentials loginCredentials = new LoginCredentials();
            loginCredentials.setEmail(emailString);
            loginCredentials.setPassword(passwordString);


            if (!loginCredentials.isEmailValid()) {
                setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.invalid_credentials));
                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
            }
            else {
                authenticationService.attemptLogin(loginCredentials.getEmail(), loginCredentials.getPassword(),
                        new AttemptLoginCallback() {
                            @Override
                            public void onAttemptLoginFailed(String errorMessage) {
                                //hide loader
                                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));

                                Log.d(TAG, "onAttemptLoginFailed: " + errorMessage);

                                //a little hack to prevent malicious users from experimenting with emails
                                if (errorMessage.contains("user"))
                                    setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.invalid_credentials));
                                else
                                    setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(errorMessage));

                            }

                            @Override
                            public void onAttemptLoginSuccess(String userAuthProviderId) {
                                //hide loader
                                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));

                                goToDashboard(userAuthProviderId);
                            }

                            @Override
                            public void onErrorOccurred(String errorMessage) {
                                Log.d(TAG, "onErrorOccurred: " + errorMessage);
                            }
                        });
            }

        }else{
            setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.invalid_credentials));
        }

    }

    public void executeOnActivityResultCalled(int requestCode, int resultCode, Intent data) {

        if(requestCode == RC_SIGN_IN){
            //google
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                validateAuthResultOnSuccessfulLogin(credential);

                // Signed in successfully, show authenticated UI.
                //goToDashboardFromAuthExtras();
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.failed_to_sign_in_google));
            }

        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }


    }

    public void completeTwitterSignIn(Task<AuthResult> startActivityForSignInWithProvider) {
        startActivityForSignInWithProvider.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onComplete: "+e.getLocalizedMessage());
                setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.failed_to_sign_in_twitter));
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                validateAuthResultOnSuccessfulLogin(authResult.getCredential());
            }
        });
    }


    public void validateAuthResultOnSuccessfulLogin(AuthCredential authCredential){
        authenticationService.attemptLoginWithCredential(authCredential,
                new AttemptLoginCallback() {
                    @Override
                    public void onAttemptLoginFailed(String errorMessage) {
                        //hide loader
                        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));

                        Log.d(TAG, "onAttemptLoginFailed: " + errorMessage);
                        setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.invalid_credentials));

                    }

                    @Override
                    public void onAttemptLoginSuccess(String userAuthProviderId) {
                        //hide loader
                        //setDestinationId(0);
                        goToDashboardFromAuthExtras(userAuthProviderId);
                        //goToDashboard();
                    }

                    @Override
                    public void onErrorOccurred(String errorMessage) {
                        Log.d(TAG, "onErrorOccurred: " + errorMessage);
                    }
                });
    }
}
