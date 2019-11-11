package com.codedevtech.portfolioapp.di.modules;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.codedevtech.authenticationserviceprovider.interface_implementations.FirebaseAuthenticationService;
import com.codedevtech.authenticationserviceprovider.interfaces.AuthenticationService;
import com.codedevtech.portfolioapp.R;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.squareup.moshi.Moshi;

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
                 .requestIdToken(application.getString(R.string.default_web_client_id))
                 .requestEmail()
                .build();

         return GoogleSignIn.getClient(application.getApplicationContext(), googleSignInOptions);
    }

    @Singleton
    @Provides
    @NonNull
    public final CallbackManager providesCallbackManager(){
        return CallbackManager.Factory.create();
    }

    @Singleton
    @Provides
    @NonNull
    public final FirebaseRemoteConfigSettings providesFirebaseRemoteConfigSettings(){
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(86400)
                .build();

        return firebaseRemoteConfigSettings;
    }

    @Singleton
    @Provides
    @NonNull
    public final FirebaseRemoteConfig providesFirebaseRemoteConfig(FirebaseRemoteConfigSettings firebaseRemoteConfigSettings){

        FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setConfigSettingsAsync(firebaseRemoteConfigSettings);

        return firebaseRemoteConfig;
    }

    @Singleton
    @Provides
    @NonNull
    public final Moshi providesMoshi(){
        return new Moshi.Builder().build();

    }


    @Singleton
    @Provides
    @NonNull
    public final OAuthProvider providesOAuthProvider(){
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

        return provider.build();
    }

}
