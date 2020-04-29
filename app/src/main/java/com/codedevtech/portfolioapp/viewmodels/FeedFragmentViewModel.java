package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.portfolioapp.commands.SnackbarCommand;
import com.codedevtech.portfolioapp.models.FeedPost;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.repositories.FirebaseFolioFeedRepository;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.Query;

import java.util.List;

import javax.inject.Inject;

public class FeedFragmentViewModel extends BaseViewModel {

    private static final String TAG = "FeedFragmentViewModel";

    private Query userFeedQuery;
    private FirebaseFolioFeedRepository dataRepositoryService;
    private SharedPreferences sharedPreferences;
    private MutableLiveData<Resource<List<FeedPost>>> userFeedLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Event<String>> shareLink = new MutableLiveData<>();


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


    public MutableLiveData<Event<String>> getShareLink() {
        return shareLink;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void generateLink(FeedPost feedPost){
        isLoading.setValue(true);
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.portfolioapp.com/post/"+feedPost.getUserId()+"/"+feedPost.getPostId()))
                .setDomainUriPrefix("https://portfolioapp.page.link")
                .setNavigationInfoParameters(
                        new DynamicLink.NavigationInfoParameters.Builder()
                                .setForcedRedirectEnabled(true)
                                .build())
                //.setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                //todo fix uri parse of null .setImageUrl(Uri.parse(feedPost.getPostImageId()))
                                .setTitle(Utility.getSharePostTitle(feedPost.getDisplayName()))
                                .setDescription(feedPost.getCaption())
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        isLoading.setValue(false);

                        if(task.isSuccessful()){

                            //setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
                            Uri shortLink = task.getResult().getShortLink();
                            Log.d(TAG, "onComplete: "+task.getResult().getPreviewLink());

                            shareLink.setValue(new Event<String>(shortLink.toString()));

                        }else{
                            //setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
                            setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(task.getException().getLocalizedMessage()));
                        }
                    }
                });


    }

}
