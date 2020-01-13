package com.codedevtech.portfolioapp.models;

import com.google.firebase.firestore.DocumentId;

public class FeedDocument {

    @DocumentId
    private String postId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
