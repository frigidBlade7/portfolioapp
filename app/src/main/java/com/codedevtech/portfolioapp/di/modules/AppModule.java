package com.codedevtech.portfolioapp.di.modules;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Provides;

public class AppModule {

    @Singleton
    @Provides
    @NonNull

    public final Context providesContext(Application application){
        return application.getApplicationContext();
    }
}
