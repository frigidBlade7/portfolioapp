package com.codedevtech.portfolioapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.commands.SnackbarCommand;
import com.codedevtech.portfolioapp.navigation.Event;


public class BaseViewModel extends AndroidViewModel {

    private MutableLiveData<Event<NavigationCommand>> navigationCommandMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Event<SnackbarCommand>> snackbarCommandMutableLiveData = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Event<NavigationCommand>> getNavigationCommandMutableLiveData() {
        return navigationCommandMutableLiveData;
    }

    //trigger navigation event
    public void setNavigationCommandMutableLiveData(NavigationCommand navigationCommand) {
        this.navigationCommandMutableLiveData.setValue(new Event<>(navigationCommand));
    }


    public MutableLiveData<Event<SnackbarCommand>> getSnackbarCommandMutableLiveData() {
        return snackbarCommandMutableLiveData;
    }

    //trigger snackbar event
    public void setSnackbarCommandMutableLiveData(SnackbarCommand snackbarCommand) {
        this.snackbarCommandMutableLiveData.setValue(new Event<>(snackbarCommand));
    }
}
