package com.codedevtech.portfolioapp.repositories.interfaces;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.repositories.FirestoreDatabaseBoundResourceDocument;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseFolioUserRepository implements DataRepositoryService<FolioUser> {

    private static final String TAG = "FirebaseFolioUserReposi";

    private CollectionReference collectionReference;
    private FirestoreDatabaseBoundResourceDocument firestoreDatabaseBoundResourceDocument;
    private final FirebaseFirestore firestoreDB;

    public FirebaseFolioUserRepository(String collectionPath) {
        this.firestoreDB = FirebaseFirestore.getInstance();
        this.collectionReference = firestoreDB.collection(collectionPath);
    }


    @Override
    public void add(final FolioUser item, final SuccessCallback successCallback) {
        collectionReference.document(item.getId()).set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    successCallback.success(item.getId());
                    Log.d(TAG, "onComplete: Success add item");
                }else{
                    successCallback.failure(task.getException().getLocalizedMessage());
                    Log.d(TAG, "onComplete: Failure add item");

                }
            }
        });
    }


    //todo edit this to provide field specific update
    @Override
    public void update(FolioUser item, SuccessCallback successCallback) {
        //todo to be implemented
        collectionReference.document(item.getId()).set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: Success update item");

                }else{
                    Log.d(TAG, "onComplete: Failed update item");

                }
            }
        });
    }

    @Override
    public void remove(final FolioUser item, final SuccessCallback successCallback) {
        //todo to be implemented
        collectionReference.document(item.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    successCallback.success(item.getId());
                    Log.d(TAG, "onComplete: remove successful");
                }
                else{
                    successCallback.failure(task.getException().getLocalizedMessage());
                    Log.d(TAG, "onComplete: remove failed");
                }
            }
        });
    }


    public LiveData<Resource<DocumentSnapshot>> getFolioUserById(String userId) {
        firestoreDatabaseBoundResourceDocument = new FirestoreDatabaseBoundResourceDocument(collectionReference.document(userId));
        return firestoreDatabaseBoundResourceDocument;
    }

    public CollectionReference getCollectionReference() {
        return collectionReference;
    }

    /*   public CollectionReference getCollectionReference() {
        return collectionReference;
    }*/
}
