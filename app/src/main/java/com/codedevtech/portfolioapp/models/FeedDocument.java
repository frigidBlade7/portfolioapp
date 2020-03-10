package com.codedevtech.portfolioapp.models;

import android.view.LayoutInflater;

import com.codedevtech.portfolioapp.R;
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
