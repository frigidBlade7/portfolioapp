package com.codedevtech.portfolioapp.repositories.interfaces;

import androidx.lifecycle.LiveData;

import com.codedevtech.portfolioapp.callbacks.SuccessCallback;

import java.util.List;

public interface DataRepositoryService<T> {

    //add item
    void add(T item, SuccessCallback successCallback);

    //update an item
    void update(T item, SuccessCallback successCallback);

    //delete an item
    void remove(T item, SuccessCallback successCallback);

/*    LiveData<T> queryItem (String specification);

    LiveData<List<T>> queryItemList (String specification);*/

}
