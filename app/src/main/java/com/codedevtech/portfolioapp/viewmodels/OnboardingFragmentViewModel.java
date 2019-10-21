package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.codedevtech.portfolioapp.R;

public class OnboardingFragmentViewModel extends BaseViewModel {


    public OnboardingFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void goToAuthentication(View view){
        setDestinationId(R.id.action_onboardingFragment_to_authenticationFragment);
    }


}
