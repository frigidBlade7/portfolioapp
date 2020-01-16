package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.utilities.Utility;

import javax.inject.Inject;

public class OnboardingFragmentViewModel extends BaseViewModel {

    private SharedPreferences sharedPreferences;
    private MutableLiveData<Event<String>> userAuthId = new MutableLiveData<>();

    @Inject
    public OnboardingFragmentViewModel(@NonNull Application application, SharedPreferences sharedPreferences) {
        super(application);
        this.sharedPreferences = sharedPreferences;

        setUserAuthId(sharedPreferences.getString(Utility.USER_AUTH_ID,""));
    }

    public void goToAuthentication(){
        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_onboardingFragment_to_authenticationFragment));
    }

    public MutableLiveData<Event<String>> getUserAuthId() {
        return userAuthId;
    }

    public void setUserAuthId(String userAuthId) {
        this.userAuthId.setValue(new Event<>(userAuthId));
    }
}
