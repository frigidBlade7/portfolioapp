package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.codedevtech.portfolioapp.repositories.interfaces.DataRepositoryService;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioUserRepository;
import com.facebook.share.Share;

import javax.inject.Inject;

public class DashboardFragmentViewModel extends BaseViewModel {

    private SharedPreferences sharedPreferences;
    private String userAuthId;
    private DataRepositoryService dataRepositoryService;

    @Inject
    public DashboardFragmentViewModel(@NonNull Application application, SharedPreferences sharedPreferences) {
        super(application);
        this.sharedPreferences = sharedPreferences;
        this.userAuthId = sharedPreferences.getString("userAuthId","");

        dataRepositoryService = new FirebaseFolioUserRepository(userAuthId);


    }
}
