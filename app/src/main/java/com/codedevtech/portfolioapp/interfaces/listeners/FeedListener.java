package com.codedevtech.portfolioapp.interfaces.listeners;

import com.codedevtech.portfolioapp.models.FeedPost;

public interface FeedListener {

    void onFeedProfilePhotoTapped(FeedPost feedPost);

    void onFeedPostShareTapped(FeedPost feedPost);
}
