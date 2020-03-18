package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.callbacks.UploadProgressCallback;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.commands.SnackbarCommand;
import com.codedevtech.portfolioapp.fragments.CompleteProfileFragmentDirections;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.interfaces.RegistrationService;
import com.codedevtech.portfolioapp.repositories.interfaces.DataRepositoryService;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioUserRepository;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CompleteProfileViewModel extends BaseViewModel {

    private static final String TAG = "CompleteProfileViewMode";

    private MutableLiveData<String> firstNameLiveData = new MutableLiveData<>();
    private MutableLiveData<String> lastNameLiveData = new MutableLiveData<>();
    private MutableLiveData<String> bioLiveData = new MutableLiveData<>();
    private String userAuthProviderId;
    private MutableLiveData<Uri> uriMutableLiveData = new MutableLiveData<>();
    private ArrayList<String> roleFlags;
    private RegistrationService registrationService;
    private SharedPreferences sharedPreferences;
    private MutableLiveData<Double> progressCount = new MutableLiveData<>();

    private MutableLiveData<String> photoUrl = new MutableLiveData<>();


    @Inject
    public CompleteProfileViewModel(@NonNull Application application, RegistrationService registrationService, SharedPreferences sharedPreferences) {
        super(application);
        this.registrationService = registrationService;
        this.roleFlags = new ArrayList<>();
        this.sharedPreferences = sharedPreferences;
    }


    public MutableLiveData<String> getPhotoUrl() {
        return photoUrl;
    }

    public MutableLiveData<Uri> getUriMutableLiveData() {
        return uriMutableLiveData;
    }

    public MutableLiveData<String> getFirstNameLiveData() {
        return firstNameLiveData;
    }

    public void setFirstNameLiveData(String firstNameLiveData) {
        this.firstNameLiveData.setValue(firstNameLiveData);
    }

    public MutableLiveData<String> getLastNameLiveData() {
        return lastNameLiveData;
    }

    public void setLastNameLiveData(String lastNameLiveData) {
        this.lastNameLiveData.setValue(lastNameLiveData);
    }

    public MutableLiveData<String> getBioLiveData() {
        return bioLiveData;
    }

    public void setBioLiveData(String bioLiveData) {
        this.bioLiveData.setValue(bioLiveData);
    }

    public void goBack(){
        setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
    }

    public void attemptCompleteProfile(Boolean isEdit){

        FolioUser folioUser = new FolioUser();
        folioUser.setId(userAuthProviderId);
        folioUser.setEmail(obtainEmail());
        folioUser.setFirstName(firstNameLiveData.getValue());
        folioUser.setLastName(lastNameLiveData.getValue());
        folioUser.setBio(bioLiveData.getValue());
        folioUser.setRoleFlags(roleFlags);
        folioUser.setPhotoUrl(photoUrl.getValue());

        if(!folioUser.isFirstNameValid()){
            setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.first_name_required));
        }
        else if(!folioUser.isLastNameValid()){
            setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.last_name_required));
        }

        else if(!folioUser.isEmailValid()){
            setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.email_retrieval_error));
        }

        else if (!folioUser.isRoleFlagsValid()){
            setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarId(R.string.select_at_least_one_role));
        }

        else{
            setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.loadingDialog));

            if(isEdit)
                updateUserData(folioUser);
            else
                storeUserData(folioUser);
        }

    }

    private void updateUserData(final FolioUser folioUser) {

        registrationService.updateUser(folioUser, new SuccessCallback() {
            @Override
            public void success(String id) {

                //save user id to shared pref
                sharedPreferences.edit().putString(Utility.USER_AUTH_ID, id).apply();
                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));

            }

            @Override
            public void failure(String message) {
                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
                setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(message));
            }
        });

    }

    private void storeUserData(final FolioUser folioUser) {

        registrationService.registerUser(folioUser, new SuccessCallback() {
            @Override
            public void success(String id) {

                //save user id to shared pref
                sharedPreferences.edit().putString(Utility.USER_AUTH_ID, id).apply();
                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));

                CompleteProfileFragmentDirections.ActionCompleteProfileFragmentToDashboardFragment action =
                        CompleteProfileFragmentDirections.actionCompleteProfileFragmentToDashboardFragment(id);



                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationAction(action));
            }

            @Override
            public void failure(String message) {
                setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));
                setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(message));
            }
        });

    }

    public void setUserAuthProviderId(String userAuthProviderId) {
        this.userAuthProviderId = userAuthProviderId;
    }

    public void fillUserFields(FolioUser folioUser) {
        roleFlags.addAll(folioUser.getRoleFlags());
        firstNameLiveData.setValue(folioUser.getFirstName());
        lastNameLiveData.setValue(folioUser.getLastName());
        bioLiveData.setValue(folioUser.getBio());
        photoUrl.setValue(folioUser.getPhotoUrl());

    }

    //get email (by using id maybe??)
    private String obtainEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    public void toggleRoleSelected(View view){


        //Log.d(TAG, "toggleRoleSelected: "+view.getId());

        String chipString = ((Chip)view).getText().toString();

        if(roleFlags.contains(chipString)){
            roleFlags.remove(chipString);
        }else{
            roleFlags.add(chipString);
        }


    }

    public MutableLiveData<Double> getProgressCount() {
        return progressCount;
    }

    public void updateProfilePhoto(Uri resultUri) {
        registrationService.updateProfilePhoto(userAuthProviderId,resultUri, new UploadProgressCallback() {
            @Override
            public void onProgress(Double progress) {
                progressCount.setValue(progress);
            }

            @Override
            public void success(String id) {
                setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(id));
                progressCount.setValue(0.0);
            }

            @Override
            public void failure(String message) {
                setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(message));
                progressCount.setValue(0.0);

            }
        });
    }
}

