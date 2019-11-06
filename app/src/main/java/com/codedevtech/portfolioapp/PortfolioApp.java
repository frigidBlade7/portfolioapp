package com.codedevtech.portfolioapp;

import android.app.Activity;
import android.app.Application;

import com.codedevtech.portfolioapp.di.models.AppInjector;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class PortfolioApp extends Application implements HasActivityInjector {

    @Inject
    public DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        AppInjector.init(this);
        super.onCreate();

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
