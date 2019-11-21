package com.codedevtech.portfolioapp.interfaces;

import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.callbacks.UserExistsCallback;
import com.codedevtech.portfolioapp.models.FolioUser;

public interface RegistrationService {

    void userExists(String userId, UserExistsCallback userExistsCallback);

    void registerUser(FolioUser folioUser, SuccessCallback successCallback);
}
