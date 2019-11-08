package com.codedevtech.portfolioapp.di.modules;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.codedevtech.authenticationserviceprovider.interface_implementations.FirebaseAuthenticationService;
import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

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

    @Singleton
    @Provides
    @NonNull
    public final GoogleSignInClient providesGoogleSignInClient(Application application){
         GoogleSignInOptions googleSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

         return GoogleSignIn.getClient(application.getApplicationContext(), googleSignInOptions);
    }
}
