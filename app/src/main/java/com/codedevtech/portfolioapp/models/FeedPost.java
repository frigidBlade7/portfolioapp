package com.codedevtech.portfolioapp.models;

import androidx.room.TypeConverters;

import com.codedevtech.portfolioapp.converters.DateConverter;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class FeedPost {

    @DocumentId
    private String postId;
    private String userId;
    private String caption;
    private String postImageId;
    private String displayName;
    private String displayPhoto;
    @TypeConverters(DateConverter.class) @ServerTimestamp
    private Date createdAt;


    public FeedPost() {
        //no args constructor
    }

    public FeedPost(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }


    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setPostImageId(String postImageId) {
        this.postImageId = postImageId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getCaption() {
        return caption;
    }

    public String getPostImageId() {
        return postImageId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getDisplayPhoto() {
        return displayPhoto;
    }

    public void setDisplayPhoto(String displayPhoto) {
        this.displayPhoto = displayPhoto;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
