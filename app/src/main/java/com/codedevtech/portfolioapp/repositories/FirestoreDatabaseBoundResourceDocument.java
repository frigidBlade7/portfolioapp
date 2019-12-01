package com.codedevtech.portfolioapp.repositories;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

//creating a class to abstract querysnapshot data (from firestore) into livedata object
//this enables us to automatically set and remove listeners whenever the livedata is available by
// overriding its onactive and oninactive methods
public class FirestoreDatabaseBoundResourceDocument extends LiveData<Resource<DocumentSnapshot>> {

    private static final String TAG = "FirestoreDBBoundRDoc";
    //the firebase query object
    private final DocumentReference documentReference;
    //class implementing the firestore event listener interface
    //we could have passed this as new , and overridden the setvalue method,
    // but that means creating a new object every time on active/inactive is called
    //and managing the query outside of this class. no thanks!

    //we will implement the class at the bottom there, thus single creation - multiple use
    private final FirestoreDocumentEventListener firestoreDocumentEventListener = new FirestoreDocumentEventListener();

    //to help deregister listeners
    private ListenerRegistration registration;

    //todo can we find a better way of doing this
    private boolean listenerRemovePending = false;
    private final Handler handler = new Handler();
    private final Runnable removeListener = new Runnable() {
        @Override
        public void run() {
            registration.remove();
            listenerRemovePending = false;
        }
    };




    //default constructor (i am unsure if we will need a default empty. i doubt
    public FirestoreDatabaseBoundResourceDocument(DocumentReference documentReference){
        this.documentReference =  documentReference;


    }

    @Override
    protected void onActive() {
        super.onActive();
        //when livedata is in active state, add listener


        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener);
        }
        else {
            //used to be just this method without the checks
            registration = documentReference.addSnapshotListener(firestoreDocumentEventListener);
        }

        listenerRemovePending = false;
    }

    @Override
    protected void onInactive() {
        //when livedata is inactive,say, fragment has been displaced remove the listener
        super.onInactive();
        // Listener removal is schedule on a two second delay

        //used to be just 'registration.remove();'  without the checks
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending = true;
    }


    private class FirestoreDocumentEventListener implements EventListener<DocumentSnapshot> {

        // from the documentation
        // onEvent will be called with the new value or the error if an error occurred.
        // It's guaranteed that exactly one of value or error will be non-null.
        //isnt that lovely???

        //todo find a place to setValue(Resource.loading)


        @Override
        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
            //the call has been made,
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                setValue(Resource.error(e.getLocalizedMessage(), documentSnapshot)); //e is not null thus documentsnapshot will be null
                return;
            }

            //no need for else block, using a return; makes sure we handle the error case first, and
            //we bypass the error block WHEN AND ONLY WHEN the error is null

            //determine if the change was sent to the server immediately or is locally stored, pending sync
            String source = documentSnapshot != null && documentSnapshot.getMetadata().hasPendingWrites()
                    ? "Local" : "Server";

            if (documentSnapshot != null && documentSnapshot.exists()) {
                Log.d(TAG, source + " data: " + documentSnapshot.getData());
                setValue(Resource.success(documentSnapshot));

            } else {
                Log.d(TAG, source + " data: null");
                setValue(Resource.error("is null", documentSnapshot)); //e is not null thus documentsnapshot will be null

            }

            //we know this will be a livedata of a single value model (non list)
        }
    }


}
