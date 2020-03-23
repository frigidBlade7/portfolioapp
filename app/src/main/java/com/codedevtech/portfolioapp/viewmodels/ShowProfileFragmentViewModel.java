package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.commands.SnackbarCommand;
import com.codedevtech.portfolioapp.interfaces.UserInteractionsService;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.models.FollowingDocument;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioFeedRepository;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioUserRepository;
import com.codedevtech.portfolioapp.service_implementations.FirebaseUserInteractionsServiceImplementation;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;

import static com.codedevtech.portfolioapp.utilities.Utility.USER_AUTH_ID;

public class ShowProfileFragmentViewModel extends BaseViewModel {

    private static final String TAG = "ShowProfileFragmentView";

    private SharedPreferences sharedPreferences;
    private FirebaseFolioUserRepository dataRepositoryServiceUser;
    private FirebaseFolioFeedRepository dataRepositoryServiceFeed;
    private Query userFeedQuery;
    private LiveData<Resource<FolioUser>> folioUserLiveData = new MutableLiveData<>();

    private UserInteractionsService userInteractionsService;


    @Inject
    public ShowProfileFragmentViewModel(@NonNull Application application, SharedPreferences sharedPreferences,
                                        UserInteractionsService userInteractionsService) {
        super(application);
        dataRepositoryServiceUser = new FirebaseFolioUserRepository("users");
        dataRepositoryServiceFeed = new FirebaseFolioFeedRepository("feed");
        this.userInteractionsService = (FirebaseUserInteractionsServiceImplementation) userInteractionsService;
        this.sharedPreferences = sharedPreferences;

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

    public void followUser(FolioUser folioUser){

        FollowingDocument followingDocument = new FollowingDocument();
        followingDocument.setId(folioUser.getId());
        followingDocument.setDisplayName(folioUser.getFirstName()+" "+folioUser.getLastName());
        followingDocument.setDisplayPhoto(folioUser.getPhotoUrl());

        userInteractionsService.followUser(sharedPreferences.getString(USER_AUTH_ID, ""),
                followingDocument, new SuccessCallback() {
                    @Override
                    public void success(String id) {
                        setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString("You are now following "+ id));
                    }

                    @Override
                    public void failure(String message) {

                    }
                });
    }

    public boolean isMe(String id){
        Log.d(TAG, "isMe: "+id);
        return sharedPreferences.getString(USER_AUTH_ID, "").equals(id);
    }

}
