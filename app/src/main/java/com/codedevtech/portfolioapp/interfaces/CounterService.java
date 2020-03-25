package com.codedevtech.portfolioapp.interfaces;

public interface CounterService {

    void createCounter(int initCountState);

    void incrementCount();

    int getCount();
}
