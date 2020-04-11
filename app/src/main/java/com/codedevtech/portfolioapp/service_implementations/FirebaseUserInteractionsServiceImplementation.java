package com.codedevtech.portfolioapp.service_implementations;

import android.util.Log;

import androidx.annotation.NonNull;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.callbacks.BooleanSuccessCallback;
import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.interfaces.UserInteractionsService;
import com.codedevtech.portfolioapp.models.Chatroom;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.models.FollowingDocument;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioUserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirebaseUserInteractionsServiceImplementation implements UserInteractionsService {

    private static final String TAG = "FirebaseUserInteraction";
    private final FirebaseFolioUserRepository firebaseUserDataRepositoryService;

    private CollectionReference collectionReference;
    private CollectionReference messagesCollectionReference;
    private CollectionReference chatroomCollectionReference;
    private final FirebaseFirestore firestoreDB;


    public FirebaseUserInteractionsServiceImplementation() {
        this.firestoreDB = FirebaseFirestore.getInstance();
        this.collectionReference = firestoreDB.collection("following");
        this.messagesCollectionReference = firestoreDB.collection("messages");
        this.chatroomCollectionReference = firestoreDB.collection("chatrooms");
        this.firebaseUserDataRepositoryService = new FirebaseFolioUserRepository("users");

    }

    @Override
    public void followUser(String myUserId, final FollowingDocument followingDocument, final SuccessCallback successCallback) {
        collectionReference.document(myUserId).collection("followingIds").document(followingDocument.getId())
                .set(followingDocument).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    successCallback.success(followingDocument.getDisplayName());
                    Log.d(TAG, "onComplete: user followed successfully");
                }
                else{
                    successCallback.failure(task.getException().getLocalizedMessage());
                    Log.d(TAG, "onComplete: failed to add user to following list");

                }
            }
        });
    }

    @Override
    public void isFollowing(String myUserId, String targetUserId, final BooleanSuccessCallback booleanSuccessCallback) {
        collectionReference.document(myUserId).collection("followingIds").document(targetUserId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists())
                        booleanSuccessCallback.booleanValue(true);
                    else
                        booleanSuccessCallback.booleanValue(false);
                }
                else{
                    booleanSuccessCallback.booleanValue(false);
                }
            }
        });
    }

    @Override
    public void unfollowUser(String myUserId, final String targetUserId, final SuccessCallback successCallback) {
        collectionReference.document(myUserId).collection("followingIds").document(targetUserId)
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    successCallback.success(targetUserId);
                    Log.d(TAG, "onComplete: user unfollowed successfully");
                }
                else{
                    successCallback.failure(task.getException().getLocalizedMessage());
                    Log.d(TAG, "onComplete: failed to remove user from following list");

                }
            }
        });
    }

    @Override
    public void messageUser(final String myUserId, final String targetUserId, final  SuccessCallback successCallback) {
        chatroomCollectionReference.whereEqualTo(targetUserId,true)
                .whereEqualTo(myUserId,true).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            if(task.getResult().isEmpty()) {
                                //todo create new chatroom


                                createNewChatRoom(myUserId, targetUserId,successCallback);
                                return;
                            }

                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            Chatroom chatroom = documentSnapshot.toObject(Chatroom.class);
                            successCallback.success(chatroom.getChatroomId());

                        }else{
                            createNewChatRoom(myUserId,targetUserId,successCallback);
                        }
                    }
                });
    }

    private void createNewChatRoom(final String myUserId,final String targetUserId, final SuccessCallback successCallback) {
        Map<String, Boolean> chatRoomData = new HashMap<>();
        chatRoomData.put(myUserId,true);
        chatRoomData.put(targetUserId,true);

        final String newChatroomId = chatroomCollectionReference.document().getId();
        chatroomCollectionReference.document(newChatroomId).set(chatRoomData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //todo go to chatroom
                    successCallback.success(newChatroomId);

                }else{
                    successCallback.failure("Cannot message this user");
                }
            }
        });
    }
}
