package com.codedevtech.portfolioapp.di.modules;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.codedevtech.authenticationserviceprovider.interface_implementations.FirebaseAuthenticationService;
import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public final class AppModule {

    @Singleton
    @Provides
    @NonNull
    public final Context providesContext(Application application){
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    @NonNull
    public final AuthenticationService providesAuthenticationService(){
        return new FirebaseAuthenticationService();
    }
}
