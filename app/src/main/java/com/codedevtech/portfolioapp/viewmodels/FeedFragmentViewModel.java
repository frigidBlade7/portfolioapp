package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import javax.inject.Inject;

public class FeedFragmentViewModel extends BaseViewModel {

    @Inject
    public FeedFragmentViewModel(@NonNull Application application) {
        super(application);
    }
}
