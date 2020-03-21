package com.codedevtech.portfolioapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codedevtech.portfolioapp.fragments.DashboardFragment;
import com.codedevtech.portfolioapp.fragments.DashboardFragmentDirections;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Map;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String TAG = "MainActivity";
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    FirebaseRemoteConfig firebaseRemoteConfig;

    @Inject
    SharedPreferences sharedPreferences;




    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d(TAG, "onNewIntent: ");

        checkDeepLink(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: ");
        //remove splash screen theme
        setTheme(R.style.AppTheme);

        this.configureDagger();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_default_values);

        //handle deep link

        checkDeepLink(getIntent());

    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }


    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    private void checkDeepLink(Intent intent){
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)


                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();

                            Log.d(TAG, "onSuccess: "+deepLink.getLastPathSegment());

                            String uid = deepLink.getLastPathSegment();
 /*                           if(sharedPreferences.getString(Utility.USER_AUTH_ID,"").equals(uid))
                                //Navigation.findNavController(MainActivity.this, R.id.fragment).navigate(R.id.profileFragment);

                            else {
                                DashboardFragmentDirections.ActionDashboardFragmentToShowProfileFragment action =
                                        DashboardFragmentDirections.actionDashboardFragmentToShowProfileFragment(deepLink.getLastPathSegment());

                                Navigation.findNavController(MainActivity.this, R.id.fragment).navigate(action);
                            }*/
                            MainNavDirections.ActionGlobalShowProfileFragment action =
                                    DashboardFragmentDirections.actionGlobalShowProfileFragment(deepLink.getLastPathSegment());

                            Navigation.findNavController(MainActivity.this, R.id.fragment).navigate(action);


                        }else{
                            Log.d(TAG, "onSuccessnewint: no pending dl ");
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });
    }
}
