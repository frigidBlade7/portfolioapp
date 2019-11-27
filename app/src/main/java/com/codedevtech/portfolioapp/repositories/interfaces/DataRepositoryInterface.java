package com.codedevtech.portfolioapp.repositories.interfaces;

public interface DataRepositoryInterface<T> {

    //add item
    void add(T item);

    //update an item
    void update(T item);

    //delete an item
    void remove(T item);

    //LiveData<T> queryItem (String specification);

}
