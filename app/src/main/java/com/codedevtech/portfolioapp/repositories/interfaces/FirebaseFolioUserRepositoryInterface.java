package com.codedevtech.portfolioapp.repositories.interfaces;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.repositories.FirestoreDatabaseBoundResourceCollection;
import com.codedevtech.portfolioapp.repositories.interfaces.DataRepositoryInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseFolioUserRepositoryInterface implements DataRepositoryInterface<FolioUser> {

    private final FirebaseFirestore firestoreDB = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;
    private FirestoreDatabaseBoundResourceCollection firestoreDatabaseBoundResourceCollection;

    public FirebaseFolioUserRepositoryInterface(String collectionPath) {
        this.collectionReference = firestoreDB.collection(collectionPath);
        this.firestoreDatabaseBoundResourceCollection = new FirestoreDatabaseBoundResourceCollection(collectionReference);
    }


    @Override
    public void add(FolioUser item) {
        collectionReference.document().set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    @Override
    public void update(FolioUser item) {
        //todo to be implemented
    }

    @Override
    public void remove(FolioUser item) {
        //todo to be implemented
        collectionReference.document().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

/*    public LiveData<Resource<QuerySnapshot>> query(String specification) {
        return firestoreDatabaseBoundResourceCollection;
    }


    public CollectionReference getCollectionReference() {
        return collectionReference;
    }*/
}
