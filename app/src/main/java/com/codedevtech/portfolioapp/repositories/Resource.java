package com.codedevtech.portfolioapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codedevtech.portfolioapp.utilities.ResourceStatus;

import java.util.Objects;

//resource class governing all data types to be obtained
public class Resource <T> {

    private static final String TAG = "Resource";



    public Resource(@NonNull ResourceStatus status, @Nullable String message, @Nullable T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    //status of resource obtainence lol
    @NonNull
    public final ResourceStatus status;

    //message associated (to be displayed to user)
    //in the case of firebase these might be preset and not editable so front end might have to
        //  verify that message is pleasant for user consumption
    @Nullable
    public final String message;

    //the actual data of generic type
    @Nullable
    public final T data;


    public Resource(@NonNull ResourceStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.message = message;
        this.data = data;

        Log.d(TAG, "Resource: "+data);
    }

    //run this method on success listener called
    public static <T> Resource<T> success(@Nullable T data){
        return new Resource<>(ResourceStatus.SUCCESS, data, null);
    }

    //run this method on cancelled or on error occured listener called
    public static <T> Resource<T> error(String message, @Nullable T data){
        return new Resource<>(ResourceStatus.ERROR, data, message);
    }

    //run this everytime the resource is just being called.
    //since we will be constantly listening for new data, we can ignore this
    //if we ever make the switch to an external api service, we might need this
    public static <T> Resource<T> loading(@Nullable T data){
        return new Resource<>(ResourceStatus.LOADING, data, null);
    }

    //convert data to different state
    public static <T> Resource<T> convertData(ResourceStatus resourceStatus, @Nullable T data, String message){
        return new Resource<>(resourceStatus, data, message);
    }



    //method that checks for equality of data
    //will be necessary if we ever decide to migrate to a different structure from cloud firestore
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (!Objects.equals(message, resource.message)) {
            return false;
        }
        return Objects.equals(data, resource.data);

        //firebase's checks may look something like this
/*        for (DocumentChange dc : snapshots.getDocumentChanges()) {
            switch (dc.getType()) {
                case ADDED:
                    Log.d(TAG, "New doc: " + dc.getDocument().getData());
                    break;
                case MODIFIED:
                    Log.d(TAG, "Modified doc: " + dc.getDocument().getData());
                    break;
                case REMOVED:
                    Log.d(TAG, "Removed doc: " + dc.getDocument().getData());
                    break;
            }
        }*/
    }

    //generate unique hashcodes
    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    //helper method to display full resource body if ever we need to check the logcat ;)
    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
