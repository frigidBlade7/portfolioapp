package com.codedevtech.portfolioapp.repositories;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

//creating a class to abstract querysnapshot data (from firestore) into livedata object
//this enables us to automatically set and remove listeners whenever the livedata is available by
// overriding its onactive and oninactive methods
public class FirestoreDatabaseBoundResource extends LiveData<Resource<QuerySnapshot>>  {

    private static final String TAG = "FirestoreDBBoundRC";
    //the firebase query object
    private final Query query;
    //class implementing the firestore event listener interface
    //we could have passed this as new , and overridden the setvalue method,
    // but that means creating a new object every time on active/inactive is called
    //and managing the query outside of this class. no thanks!

    //we will implement the class at the bottom there, thus single creation - multiple use
    private final FirestoreEventListener firestoreEventListener = new FirestoreEventListener();

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
    public FirestoreDatabaseBoundResource(Query query){
        this.query =  query;

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
            registration = query.addSnapshotListener(firestoreEventListener);
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


    private class FirestoreEventListener implements EventListener<QuerySnapshot> {

        // from the documentation
        // onEvent will be called with the new value or the error if an error occurred.
        // It's guaranteed that exactly one of value or error will be non-null.
        //isnt that lovely???
        @Override
        public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {

            //the call has been made,
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                //e is not null thus querysnapshot will be null
                setValue(Resource.error(e.getLocalizedMessage(), querySnapshot));
                return;
            }

            //no need for else block, using a return; makes sure we handle the error case first, and
            //we bypass the error block WHEN AND ONLY WHEN the error is null

            //we know this will be a livedata of a list
            //setValue(Resource.success(querySnapshot));

            //determine if the change was sent to the server immediately or is locally stored, pending sync
            String source = querySnapshot != null && querySnapshot.getMetadata().hasPendingWrites()
                    ? "Local" : "Server";

            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                Log.d(TAG, source + " data: " + querySnapshot.getDocuments());
                setValue(Resource.success(querySnapshot));

            } else {
                Log.d(TAG, source + " data: null");
                setValue(Resource.error("is null", querySnapshot)); //e is not null thus documentsnapshot will be null

            }
        }


    }


}
