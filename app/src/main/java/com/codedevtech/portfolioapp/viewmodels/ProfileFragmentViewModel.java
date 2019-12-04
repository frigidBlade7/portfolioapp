package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioUserRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

public class ProfileFragmentViewModel extends BaseViewModel {

    private MutableLiveData<FolioUser> userLiveData = new MutableLiveData<>();

    @Inject
    public ProfileFragmentViewModel(@NonNull Application application, SharedPreferences sharedPreferences) {
        super(application);

    }

    public LiveData<FolioUser> getUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(FolioUser folioUser) {
        this.userLiveData.setValue(folioUser);
    }
}
