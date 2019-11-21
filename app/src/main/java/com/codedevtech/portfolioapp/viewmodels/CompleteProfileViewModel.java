package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.interfaces.RegistrationService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import javax.inject.Inject;

public class CompleteProfileViewModel extends BaseViewModel {

    private static final String TAG = "CompleteProfileViewMode";

    private MutableLiveData<String> firstNameLiveData = new MutableLiveData<>();
    private MutableLiveData<String> lastNameLiveData = new MutableLiveData<>();
    private MutableLiveData<String> bioLiveData = new MutableLiveData<>();
    private String userAuthProviderId;
    private MutableLiveData<List<String>> roleFlags = new MutableLiveData<>();
    private RegistrationService registrationService;


    @Inject
    public CompleteProfileViewModel(@NonNull Application application, RegistrationService registrationService) {
        super(application);
        this.registrationService = registrationService;
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

    public void attemptCompleteProfile(View view){

        FolioUser folioUser = new FolioUser();
        folioUser.setId(userAuthProviderId);
        folioUser.setEmail(obtainEmail());
        folioUser.setFirstName(firstNameLiveData.getValue());
        folioUser.setLastName(lastNameLiveData.getValue());
        folioUser.setBio(bioLiveData.getValue());
        folioUser.setRoleFlags(roleFlags.getValue());



    }

    public void setUserAuthProviderId(String userAuthProviderId) {
        this.userAuthProviderId = userAuthProviderId;
    }

    //get email (by using id maybe??)
    public String obtainEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
}
