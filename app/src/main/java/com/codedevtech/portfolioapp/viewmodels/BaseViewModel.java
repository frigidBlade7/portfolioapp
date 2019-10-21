package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.portfolioapp.navigation.Event;


public class BaseViewModel extends AndroidViewModel {
    private MutableLiveData<Event<String>> snackbarMessage = new MutableLiveData<>();
    private MutableLiveData<Event<Integer>> destinationId = new MutableLiveData<>();


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Event<Integer>> getDestinationId() {
        return destinationId;
    }

    //trigger destination event
    public void setDestinationId(int destinationId) {
        this.destinationId.setValue(new Event<>(destinationId));
    }


    public MutableLiveData<Event<String>> getSnackbarMessage() {
        return snackbarMessage;
    }

    //trigger snackbar event
    public void setSnackbarMessage(String snackbarMessage) {
        this.snackbarMessage.setValue(new Event<>(snackbarMessage));
    }



}
