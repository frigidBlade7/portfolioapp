package com.codedevtech.portfolioapp.service_implementations;

import androidx.annotation.NonNull;

import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.callbacks.UserExistsCallback;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.interfaces.RegistrationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseRegistrationService implements RegistrationService {

    private final FirebaseFirestore firestoreDb;

    public FirebaseRegistrationService() {
        firestoreDb = FirebaseFirestore.getInstance();
    }

    @Override
    public void userExists(final String userAuthProviderId, final UserExistsCallback userExistsCallback) {
        firestoreDb.collection("users").document(userAuthProviderId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){

                        FolioUser folioUser = task.getResult().toObject(FolioUser.class);
                        userExistsCallback.userExists(folioUser);

                    }else{
                        userExistsCallback.userDoesNotExist(userAuthProviderId);
                    }
                }else{
                    userExistsCallback.error(task.getException().getLocalizedMessage());
                }
            }
        });
    }


    @Override
    public void registerUser(final FolioUser folioUser, final SuccessCallback successCallback) {

        firestoreDb.collection("users").document(folioUser.getId())
                .set(folioUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    successCallback.success(folioUser.getId());
                }else{
                    successCallback.failure(task.getException().getLocalizedMessage());
                }
            }
        });
    }
}