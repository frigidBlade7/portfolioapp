package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.facebook.share.Share;

import javax.inject.Inject;

public class DashboardFragmentViewModel extends BaseViewModel {

    private SharedPreferences sharedPreferences;

    @Inject
    public DashboardFragmentViewModel(@NonNull Application application, SharedPreferences sharedPreferences) {
        super(application);
        this.sharedPreferences = sharedPreferences;
    }
}
