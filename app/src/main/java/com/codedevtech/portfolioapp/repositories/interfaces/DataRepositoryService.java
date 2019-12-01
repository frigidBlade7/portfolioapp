package com.codedevtech.portfolioapp.repositories.interfaces;

import androidx.lifecycle.LiveData;

import com.codedevtech.portfolioapp.callbacks.SuccessCallback;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.google.firebase.firestore.QuerySnapshot;

public interface DataRepositoryService<T> {

    //add item
    void add(T item, SuccessCallback successCallback);

    //update an item
    void update(T item, SuccessCallback successCallback);

    //delete an item
    void remove(T item, SuccessCallback successCallback);

/*    LiveData<Resource<QuerySnapshot>> queryItem (String specificationPath);


   LiveData<List<T>> queryItemList (String specification);*/

}
