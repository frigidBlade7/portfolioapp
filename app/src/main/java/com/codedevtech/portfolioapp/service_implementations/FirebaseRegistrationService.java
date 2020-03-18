package com.codedevtech.portfolioapp.service_implementations;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.callbacks.UploadProgressCallback;
import com.codedevtech.portfolioapp.callbacks.UserExistsCallback;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.interfaces.RegistrationService;
import com.codedevtech.portfolioapp.repositories.interfaces.DataRepositoryService;
import com.codedevtech.portfolioapp.repositories.interfaces.FirebaseFolioUserRepository;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseRegistrationService implements RegistrationService {

    private FirebaseFolioUserRepository firebaseUserDataRepositoryService;

    private static final String TAG = "FirebaseRegistrationSer";

    public FirebaseRegistrationService() {
        firebaseUserDataRepositoryService = new FirebaseFolioUserRepository("users");
    }

    @Override
    public void userExists(final String userAuthProviderId, final UserExistsCallback userExistsCallback) {
        //todo create an exists implementation in your firebaseuserdatarepository class
        firebaseUserDataRepositoryService.getCollectionReference().document(userAuthProviderId).get()
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

        /*        firestoreDb.collection("users").document(folioUser.getId())
                .set(folioUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    successCallback.success(folioUser.getId());
                }else{
                    successCallback.failure(task.getException().getLocalizedMessage());
                }
            }
        });*/

        //edited to use datarepositoryservice
        firebaseUserDataRepositoryService.add(folioUser,successCallback);


    }

    @Override
    public void updateUser(final FolioUser folioUser, final SuccessCallback successCallback) {
        firebaseUserDataRepositoryService.update(folioUser,successCallback);
    }

    @Override
    public void updateProfilePhoto(final String userId, Uri uri, final UploadProgressCallback uploadProgressCallback) {
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("users").child(userId + ".JPG");
        final UploadTask uploadTask;

        if (uri != null) {
            uploadTask = storageReference.putFile(uri);

        } else {
            uploadProgressCallback.failure("Could not find image");
            return;
        }

        uploadTask
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        Double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        uploadProgressCallback.onProgress(progress);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadProgressCallback.failure(e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            uploadProgressCallback.failure(task.getException().getMessage());
                            throw task.getException();

                        }
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            firebaseUserDataRepositoryService.getCollectionReference().document(userId).update("photoUrl", task.getResult().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                uploadProgressCallback.success("Upload successful");
                                            }else{
                                                Log.d(TAG, "onComplete: "+task.getException().getLocalizedMessage());
                                                uploadProgressCallback.failure(task.getException().getMessage());

                                            }
                                        }
                                    });
                        }else{
                            Log.d(TAG, "onComplete: "+task.getException().getLocalizedMessage());
                            uploadProgressCallback.failure(task.getException().getLocalizedMessage());

                        }

                    }
                });

            }
        });
    }
}
