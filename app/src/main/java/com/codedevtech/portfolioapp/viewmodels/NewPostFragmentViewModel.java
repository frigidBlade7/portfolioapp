package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import javax.inject.Inject;

public class NewPostFragmentViewModel extends BaseViewModel {

    private static final String TAG = "NewPostFragmentViewModel";

    @Inject
    public NewPostFragmentViewModel(@NonNull Application application) {
        super(application);

    }
}