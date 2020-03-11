package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import javax.inject.Inject;

public class MessagesFragmentViewModel extends BaseViewModel {

    @Inject
    public MessagesFragmentViewModel(@NonNull Application application) {
        super(application);
    }
}
