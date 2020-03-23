package com.codedevtech.portfolioapp.interfaces;

import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.models.FollowingDocument;

public interface UserInteractionsService {

    void followUser(String myUserId, FollowingDocument followingDocument, SuccessCallback successCallback);
}
