package com.codedevtech.portfolioapp.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Map;

public class Chatroom {
    @DocumentId
    private String chatroomId;
    private String lastMessage;

    @ServerTimestamp
    private Date lastTimestamp;


    public String getChatroomId() {
        return chatroomId;
    }

    public String getLastMessage() {
        if(lastMessage==null)
            return "";
        return lastMessage;
    }
}
