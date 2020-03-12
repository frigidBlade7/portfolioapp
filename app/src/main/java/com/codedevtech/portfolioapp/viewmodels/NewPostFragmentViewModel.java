package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.models.FeedPost;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioFeedRepository;

import javax.inject.Inject;

public class NewPostFragmentViewModel extends BaseViewModel {

    private static final String TAG = "NewPostFragmentViewMode";
    private FirebaseFolioFeedRepository dataRepositoryService;
    private MutableLiveData<String> caption = new MutableLiveData<>();
    private MutableLiveData<Bitmap> bitmap = new MutableLiveData<>();


    @Inject
    public NewPostFragmentViewModel(@NonNull Application application) {
        super(application);

        dataRepositoryService = new FirebaseFolioFeedRepository("feed");

    }

    public MutableLiveData<Bitmap> getBitmap() {
        return bitmap;
    }

    public MutableLiveData<String> getCaption() {
        return caption;
    }

    public void sendPost(FolioUser folioUser){
        FeedPost feedPost = new FeedPost();
        feedPost.setCaption(getCaption().getValue());
        feedPost.setDisplayName(String.format(getApplication().getString(R.string.full_name),folioUser.getFirstName(), folioUser.getLastName()));
        feedPost.setDisplayPhoto(folioUser.getId());
        feedPost.setUserId(folioUser.getId());

        if(bitmap.getValue()!=null){
            //feedPost.set
        }

        dataRepositoryService.add(feedPost, new SuccessCallback() {
            @Override
            public void success(String id) {
                Log.d(TAG, "success: added feedpost");
                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
            }

            @Override
            public void failure(String message) {
                Log.d(TAG, "failed: adding feedpost");
                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));

            }
        });
    }
}