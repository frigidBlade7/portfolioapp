package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.models.FeedPost;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.repositories.FirebaseFolioFeedRepository;
import com.codedevtech.portfolioapp.repositories.FirebaseFolioUserRepository;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;

public class DashboardFragmentViewModel extends BaseViewModel {

    private FirestorePagingOptions<FeedPost> options;
    private SharedPreferences sharedPreferences;
    private String userAuthId;
    private FirebaseFolioUserRepository dataRepositoryService;
    private Query userFeedQuery;
    private MutableLiveData<Integer> feedPageCount = new MutableLiveData<>(0);
    private FirebaseFolioFeedRepository feedDataRepositoryService;
    private LiveData<Resource<FolioUser>> folioUserLiveData = new MutableLiveData<>();

    @Inject
    public DashboardFragmentViewModel(@NonNull Application application, SharedPreferences sharedPreferences) {
        super(application);
        this.sharedPreferences = sharedPreferences;
        dataRepositoryService = new FirebaseFolioUserRepository("users");
        feedDataRepositoryService = new FirebaseFolioFeedRepository("feed");

    }

    public void setOptions(FirestorePagingOptions<FeedPost> options) {
        this.options = options;
    }

    public FirestorePagingOptions<FeedPost> getOptions() {
        return options;
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
        this.folioUserLiveData =    Transformations.map(dataRepositoryService.getFolioUserById(userAuthId), new FirestoreFolioUserDeserializer());
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

    public void goToNewPost(){
        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_dashboardFragment_to_newPostFragment));
    }


    public Query getUserFeedQuery() {
        return userFeedQuery;
    }

    public void setQueryLiveData() {
        this.userFeedQuery = feedDataRepositoryService.getPaginatedFeedPosts(sharedPreferences.getString(Utility.USER_AUTH_ID,""));
    }

    public MutableLiveData<Integer> getFeedPageCount() {
        return feedPageCount;
    }
}
