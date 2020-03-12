package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

public class NewPostFragmentViewModel extends BaseViewModel {

    private static final String TAG = "NewPostFragmentViewModel";
    private MutableLiveData<String> caption = new MutableLiveData<>();


    @Inject
    public NewPostFragmentViewModel(@NonNull Application application) {
        super(application);


    }

    public MutableLiveData<String> getCaption() {
        return caption;
    }

}