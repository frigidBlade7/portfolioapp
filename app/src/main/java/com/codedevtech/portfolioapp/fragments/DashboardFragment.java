package com.codedevtech.portfolioapp.fragments;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import androidx.viewpager2.widget.ViewPager2;

import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.adapters.state.DashboardAdapter;
import com.codedevtech.portfolioapp.databinding.FragmentDashboardBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.viewmodels.DashboardFragmentViewModel;
import com.facebook.share.Share;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements Injectable{

    private static final String TAG = "DashboardFragment";
    private DashboardFragmentViewModel dashboardFragmentViewModel;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Inject
    ViewModelProvider.Factory viewmodelFactory;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        //observe all changes here
/*
        dashboardFragmentViewModel.getFolioUserLiveData().observe(this.getViewLifecycleOwner(), new Observer<Resource<FolioUser>>() {
            @Override
            public void onChanged(Resource<FolioUser> folioUserResource) {
                Log.d(TAG, "onChanged: "+folioUserResource.data.getId());

            }
        });*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDashboardBinding fragmentDashboardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);

//        tie the bottom nav with the nested fragment (make sure you get the id of the fragment right)
//        NavigationUI.setupWithNavController(fragmentDashboardBinding.bottomNavigationView,
//                NavHostFragment.findNavController(getChildFragmentManager().findFragmentById(R.id.fragment)));

        ViewPager2 fragmentViewPager = fragmentDashboardBinding.fragment;
        DashboardAdapter dashboardAdapter = new DashboardAdapter(this);
        fragmentViewPager.setAdapter(dashboardAdapter);
        fragmentViewPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setAlpha(0f);
                page.setVisibility(View.VISIBLE);

                // Start Animation for a short period of time
                page.animate().alpha(1f)
                        .setDuration(page.getResources().getInteger(android.R.integer.config_shortAnimTime));
            }
        });

        new TabLayoutMediator(fragmentDashboardBinding.bottomNavigationView, fragmentViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        }).attach();


        dashboardFragmentViewModel = ViewModelProviders.of(getParentFragment(), viewmodelFactory)
                .get(DashboardFragmentViewModel.class);

        fragmentDashboardBinding.setLifecycleOwner(this.getViewLifecycleOwner());

        //get userauthid from navargs
        String userAuthId = DashboardFragmentArgs.fromBundle(getArguments()).getUserId();


        //instantiate viewmodel from onviewcreated to allow userauthid to be retrieved first



        //save userauthid to dashboard fragment
        dashboardFragmentViewModel.setUserAuthId(userAuthId);
        //save userauthid to shared prefs
        dashboardFragmentViewModel.saveUserAuthIdToSharedPrefs(userAuthId);

        dashboardFragmentViewModel.setFolioUserLiveData(userAuthId);

        return fragmentDashboardBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}
