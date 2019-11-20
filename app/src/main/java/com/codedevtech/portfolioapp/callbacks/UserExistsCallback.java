package com.codedevtech.portfolioapp.callbacks;

import com.codedevtech.portfolioapp.di.models.FolioUser;

public interface UserExistsCallback {

    void userExists(FolioUser folioUser);

    void userDoesNotExist(String userId);

    void error(String message);

}
