package com.codedevtech.portfolioapp.models;

public class OnboardingModel {
    private int description;
    private int imageId;

    public OnboardingModel(int description, int imageId) {
        this.description = description;
        this.imageId = imageId;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
