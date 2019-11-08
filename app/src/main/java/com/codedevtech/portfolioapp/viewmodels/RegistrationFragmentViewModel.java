package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.codedevtech.portfolioapp.R;

public class RegistrationFragmentViewModel extends BaseViewModel {

    public RegistrationFragmentViewModel(@NonNull Application application) {
        super(application);
    }


    public void goToCompleteProfile(View view){
        setDestinationId(R.id.action_registrationFragment_to_completeProfileFragment);
    }


}
