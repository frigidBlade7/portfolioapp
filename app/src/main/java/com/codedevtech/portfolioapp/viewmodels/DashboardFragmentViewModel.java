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
import com.codedevtech.portfolioapp.utilities.Utility;
import com.facebook.share.Share;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.codedevtech.portfolioapp.utilities.Utility.USER_AUTH_ID;

public class DashboardFragmentViewModel extends BaseViewModel {

    private SharedPreferences sharedPreferences;
    private String userAuthId;
    private FirebaseFolioUserRepository dataRepositoryService;

    private LiveData<Resource<FolioUser>> folioUserLiveData = new MutableLiveData<>();

    @Inject
    public DashboardFragmentViewModel(@NonNull Application application, SharedPreferences sharedPreferences) {
        super(application);
        this.sharedPreferences = sharedPreferences;
        dataRepositoryService = new FirebaseFolioUserRepository("users");

    }


    private class FirestoreFolioUserDeserializer implements Function<Resource<DocumentSnapshot>, Resource<FolioUser>>{

        private static final String TAG = "FirestoreFolioUserDeser";

        @Override
        public Resource<FolioUser> apply(Resource<DocumentSnapshot> input) {

            Log.d(TAG, "apply: "+input);
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

    public void setFolioUserLiveData(String userAuthId) {
        this.folioUserLiveData = Transformations.map(dataRepositoryService.getFolioUserById(userAuthId), new FirestoreFolioUserDeserializer());
    }

    public void saveUserAuthIdToSharedPrefs(String userAuthId) {
        sharedPreferences.edit().putString(Utility.USER_AUTH_ID,userAuthId).apply();
    }

    public void setUserAuthId(String userAuthId) {
        this.userAuthId = userAuthId;
    }

    public String getUserAuthId() {
        return userAuthId;
    }
}
