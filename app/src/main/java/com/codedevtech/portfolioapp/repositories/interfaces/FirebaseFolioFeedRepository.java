package com.codedevtech.portfolioapp.repositories.interfaces;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.models.FeedPost;
import com.codedevtech.portfolioapp.repositories.FirestoreDatabaseBoundResourceCollection;
import com.codedevtech.portfolioapp.repositories.FirestoreDatabaseBoundResourceDocument;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseFolioFeedRepository implements DataRepositoryService<FeedPost> {

    private static final String TAG = "FirebaseFolioFeedReposi";

    private CollectionReference collectionReference;
    private FirestoreDatabaseBoundResourceCollection firestoreDatabaseBoundResourceCollection;
    private final FirebaseFirestore firestoreDB;

    public FirebaseFolioFeedRepository(String collectionPath) {

        this.firestoreDB = FirebaseFirestore.getInstance();
        this.collectionReference = firestoreDB.collection(collectionPath);
    }

    @Override
    public void add(final FeedPost item, final SuccessCallback successCallback) {
        collectionReference.add(item).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    successCallback.success(task.getResult().getId());
                    Log.d(TAG, "onComplete: successfully added"+ task.getResult().getId());
                }else{
                    successCallback.failure(task.getException().getLocalizedMessage());
                    Log.d(TAG, "onComplete: failed to add feedpost");
                }
            }
        });

    }

    @Override
    public void update(FeedPost item, SuccessCallback successCallback) {

        //todo implement if i ever decide to enable edit post privileges

    }

    @Override
    public void remove(final FeedPost item, final SuccessCallback successCallback) {

        collectionReference.document(item.getPostId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    successCallback.success(item.getPostId());
                    Log.d(TAG, "onComplete: successfully added"+ item.getPostId());
                }else{
                    successCallback.failure(task.getException().getLocalizedMessage());
                    Log.d(TAG, "onComplete: failed to add feedpost");
                }
            }
        });

    }


    public LiveData<Resource<QuerySnapshot>> getFeedPosts(List<String> followingUserIdsList){
        collectionReference.("createdAt")
    }
}
