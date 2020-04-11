package com.codedevtech.portfolioapp.interfaces;

import com.codedevtech.portfolioapp.callbacks.BooleanSuccessCallback;
import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.models.FollowingDocument;

public interface UserInteractionsService {

    void followUser(String myUserId, FollowingDocument followingDocument, SuccessCallback successCallback);

    void isFollowing(String myUserId, String targetUserId, BooleanSuccessCallback booleanSuccessCallback);

    void unfollowUser(String myUserId, String targetUserId, SuccessCallback successCallback);

    void messageUser(String myUserId, String targetUserId, SuccessCallback successCallback);
}
