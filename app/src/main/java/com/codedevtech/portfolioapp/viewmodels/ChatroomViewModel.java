package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import javax.inject.Inject;

public class ChatroomViewModel extends BaseViewModel{

    private String chatroomId;
    @Inject
    public ChatroomViewModel(@NonNull Application application) {
        super(application);
    }
}
