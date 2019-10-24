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


}
