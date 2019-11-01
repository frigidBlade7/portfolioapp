package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.codedevtech.portfolioapp.R;

public class AuthenticationFragmentViewModel extends BaseViewModel {

    public AuthenticationFragmentViewModel(@NonNull Application application) {
        super(application);
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

}
