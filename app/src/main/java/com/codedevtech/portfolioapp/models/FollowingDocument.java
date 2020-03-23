package com.codedevtech.portfolioapp.models;

import com.google.firebase.firestore.DocumentId;

public class FollowingDocument {

    private String id;
    private String displayName;
    private String displayPhoto;


    public FollowingDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayPhoto() {
        return displayPhoto;
    }

    public void setDisplayPhoto(String displayPhoto) {
        this.displayPhoto = displayPhoto;
    }
}
