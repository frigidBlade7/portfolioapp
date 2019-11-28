package com.codedevtech.portfolioapp.repositories.interfaces;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.repositories.FirestoreDatabaseBoundResource;
import com.codedevtech.portfolioapp.repositories.FirestoreDatabaseBoundResourceCollection;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.repositories.interfaces.DataRepositoryInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseFolioUserRepository implements DataRepositoryInterface<FolioUser> {

    private static final String TAG = "FirebaseFolioUserReposi";

    private final FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;
    private FirestoreDatabaseBoundResource firestoreDatabaseBoundResource;
    private SuccessCallback successCallback;

    public FirebaseFolioUserRepository(String collectionPath, SuccessCallback successCallback) {
        this.collectionReference = firestoreDB.collection(collectionPath);
        this.firestoreDatabaseBoundResource = new FirestoreDatabaseBoundResource(collectionReference);
        this.successCallback = successCallback;
    }


    @Override
    public void add(FolioUser item) {
        collectionReference.add(item).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    //successCallback.success();
                    Log.d(TAG, "onComplete: Success add item");
                }else{
                    Log.d(TAG, "onComplete: Failure add item");

                }
            }
        });
    }

    @Override
    public void update(FolioUser item) {
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
    public void remove(FolioUser item) {
        //todo to be implemented
        collectionReference.document(item.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: remove successful");
                }
                else{
                    Log.d(TAG, "onComplete: remove failed");
                }
            }
        });
    }

    public LiveData<Resource<QuerySnapshot>> getFolioUserById(String userId) {
        return firestoreDatabaseBoundResource;
    }


 /*   public CollectionReference getCollectionReference() {
        return collectionReference;
    }*/
}
