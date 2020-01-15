package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.app.DownloadManager;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.portfolioapp.models.FeedPost;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioFeedRepository;
import com.google.firebase.firestore.Query;

import java.util.List;

import javax.inject.Inject;

public class FeedFragmentViewModel extends BaseViewModel {

    private FirebaseFolioFeedRepository dataRepositoryService;

    //private LiveData<Resource<List<FeedPost>>> userFeedLiveData = new MutableLiveData<>();
    private MutableLiveData<Query> queryLiveData = new MutableLiveData<>();
    private String userAuthId;


    @Inject
    public FeedFragmentViewModel(@NonNull Application application, SharedPreferences sharedPreferences) {
        super(application);

        userAuthId = sharedPreferences.getString("userAuth","");
        dataRepositoryService = new FirebaseFolioFeedRepository("feed");

        setQueryLiveData(dataRepositoryService.getPaginatedFeedPosts(userAuthId));

    }

    public LiveData<Query> getQueryLiveData() {
        return queryLiveData;
    }

    public void setQueryLiveData(Query liveData) {
        this.queryLiveData.setValue(liveData);
    }
}
