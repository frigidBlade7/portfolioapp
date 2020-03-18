package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.adapters.pagination.FireStoreFeedDocumentPagingAdapter;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.models.FeedPost;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioFeedRepository;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.Query;

import java.util.List;

import javax.inject.Inject;

public class FeedFragmentViewModel extends BaseViewModel {

    private FirebaseFolioFeedRepository dataRepositoryService;
    private SharedPreferences sharedPreferences;
    private MutableLiveData<Resource<List<FeedPost>>> userFeedLiveData = new MutableLiveData<>();
    private Query userFeedQuery;


    @Inject
    public FeedFragmentViewModel(@NonNull Application application, SharedPreferences sharedPreferences) {
        super(application);

        this.sharedPreferences = sharedPreferences;
        dataRepositoryService = new FirebaseFolioFeedRepository("feed");



    }

    public Query getUserFeedQuery() {
        return userFeedQuery;
    }

    public void setQueryLiveData() {
        this.userFeedQuery = dataRepositoryService.getPaginatedFeedPosts(sharedPreferences.getString(Utility.USER_AUTH_ID,""));
    }



}
