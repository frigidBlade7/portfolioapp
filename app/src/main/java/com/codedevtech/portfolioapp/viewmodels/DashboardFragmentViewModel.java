package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.repositories.interfaces.DataRepositoryService;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioUserRepository;
import com.facebook.share.Share;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DashboardFragmentViewModel extends BaseViewModel {

    private SharedPreferences sharedPreferences;
    private String userAuthId;
    private FirebaseFolioUserRepository dataRepositoryService;

    private LiveData<Resource<FolioUser>> folioUserLiveData = new MutableLiveData<>();

    @Inject
    public DashboardFragmentViewModel(@NonNull Application application, SharedPreferences sharedPreferences) {
        super(application);
        this.sharedPreferences = sharedPreferences;
        this.userAuthId = sharedPreferences.getString("userAuthId","");

        dataRepositoryService = new FirebaseFolioUserRepository("users");

        folioUserLiveData = Transformations.map(dataRepositoryService.getFolioUserById(userAuthId), new FirestoreFolioUserDeserializer());


    }


    private class FirestoreFolioUserDeserializer implements Function<Resource<DocumentSnapshot>, Resource<FolioUser>>{

        @Override
        public Resource<FolioUser> apply(Resource<DocumentSnapshot> input) {

            //convert document snapshot to folio user
            DocumentSnapshot documentSnapshot = input.data;
            FolioUser user = documentSnapshot.toObject(FolioUser.class);
            //wrap resulting user in a Resource object
            return Resource.convertData(input.status, user, input.message);
        }
    }

    public LiveData<Resource<FolioUser>> getFolioUserLiveData() {
        return folioUserLiveData;
    }
}
