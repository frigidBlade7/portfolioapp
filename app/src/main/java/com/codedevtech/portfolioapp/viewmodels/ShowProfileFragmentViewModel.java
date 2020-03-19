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
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioFeedRepository;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioUserRepository;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;

public class ShowProfileFragmentViewModel extends BaseViewModel {

    private SharedPreferences sharedPreferences;
    private FirebaseFolioUserRepository dataRepositoryServiceUser;
    private FirebaseFolioFeedRepository dataRepositoryServiceFeed;
    private Query userFeedQuery;
    private LiveData<Resource<FolioUser>> folioUserLiveData = new MutableLiveData<>();

    @Inject
    public ShowProfileFragmentViewModel(@NonNull Application application) {
        super(application);
        dataRepositoryServiceUser = new FirebaseFolioUserRepository("users");
        dataRepositoryServiceFeed = new FirebaseFolioFeedRepository("feed");

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
        this.folioUserLiveData = Transformations.map(dataRepositoryServiceUser.getFolioUserById(userAuthId), new FirestoreFolioUserDeserializer());
    }

    public Query getUserFeedQuery() {
        return userFeedQuery;
    }

    public void setQueryLiveData(String userId) {
        this.userFeedQuery = dataRepositoryServiceFeed.getPaginatedFeedPosts(userId);
    }


}
