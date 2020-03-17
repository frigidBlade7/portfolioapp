package com.codedevtech.portfolioapp.interfaces;

import android.net.Uri;

import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.callbacks.UploadProgressCallback;
import com.codedevtech.portfolioapp.callbacks.UserExistsCallback;
import com.codedevtech.portfolioapp.models.FolioUser;

public interface RegistrationService {

    void userExists(String userId, UserExistsCallback userExistsCallback);

    void registerUser(FolioUser folioUser, SuccessCallback successCallback);

    void updateUser(FolioUser folioUser, SuccessCallback successCallback);

    void updateProfilePhoto(String userId, Uri uri, UploadProgressCallback uploadProgressCallback);
}
