package com.codedevtech.portfolioapp.service_implementations;

import android.util.Log;

import androidx.annotation.NonNull;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.interfaces.UserInteractionsService;
import com.codedevtech.portfolioapp.models.FollowingDocument;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUserInteractionsServiceImplementation implements UserInteractionsService {

    private static final String TAG = "FirebaseUserInteraction";

    private CollectionReference collectionReference;
    private final FirebaseFirestore firestoreDB;

    public FirebaseUserInteractionsServiceImplementation() {
        this.firestoreDB = FirebaseFirestore.getInstance();
        this.collectionReference = firestoreDB.collection("following");

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
}
