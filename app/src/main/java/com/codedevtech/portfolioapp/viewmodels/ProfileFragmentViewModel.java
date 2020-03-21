package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.commands.SnackbarCommand;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioUserRepository;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.DocumentSnapshot;

import javax.inject.Inject;

public class ProfileFragmentViewModel extends BaseViewModel {

    private static final String TAG = "ProfileFragmentViewMode";

    private MutableLiveData<Event<String>> shareLink = new MutableLiveData<>();

    @Inject
    public ProfileFragmentViewModel(@NonNull Application application) {
        super(application);

    }

    public void generateLink(FolioUser folioUser){

        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.portfolioapp.com/users/"+folioUser.getId()))
                .setDomainUriPrefix("https://portfolioapp.page.link")
                .setNavigationInfoParameters(
                        new DynamicLink.NavigationInfoParameters.Builder()
                                .setForcedRedirectEnabled(true)
                                .build())
                //.setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setImageUrl(Uri.parse(folioUser.getPhotoUrl()))
                                .setTitle(Utility.getShareTitle(folioUser.getFirstName()))
                                .setDescription(folioUser.getBio())
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if(task.isSuccessful()){

                            setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
                            Uri shortLink = task.getResult().getShortLink();
                            Log.d(TAG, "onComplete: "+task.getResult().getPreviewLink());

                            shareLink.setValue(new Event<String>(shortLink.toString()));

                        }else{
                            setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
                            setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(task.getException().getLocalizedMessage()));
                        }
                    }
                });


    }

    public MutableLiveData<Event<String>> getShareLink() {
        return shareLink;
    }
}
