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
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioUserRepository;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

public class ShowProfileFragmentViewModel extends BaseViewModel {

    private SharedPreferences sharedPreferences;
    private FirebaseFolioUserRepository dataRepositoryService;

    private LiveData<Resource<FolioUser>> folioUserLiveData = new MutableLiveData<>();

    @Inject
    public ShowProfileFragmentViewModel(@NonNull Application application) {
        super(application);
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


}
