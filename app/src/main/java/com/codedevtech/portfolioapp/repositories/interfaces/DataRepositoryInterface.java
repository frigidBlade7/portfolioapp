package com.codedevtech.portfolioapp.repositories.interfaces;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface DataRepositoryInterface<T> {

    //add item
    void add(T item);

    //update an item
    void update(T item);

    //delete an item
    void remove(T item);

/*    LiveData<T> queryItem (String specification);

    LiveData<List<T>> queryItemList (String specification);*/

}
