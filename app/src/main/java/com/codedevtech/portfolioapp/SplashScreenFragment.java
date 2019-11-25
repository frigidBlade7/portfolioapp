package com.codedevtech.portfolioapp;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.databinding.FragmentSplashScreenBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.fragments.OnboardingFragment;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.codedevtech.portfolioapp.viewmodels.OnboardingFragmentViewModel;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreenFragment extends Fragment implements Injectable {

    private static final String TAG = "SplashScreenFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public SplashScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentSplashScreenBinding fragmentSplashScreenBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false);

        final OnboardingFragmentViewModel onboardingFragmentViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(OnboardingFragmentViewModel.class);

        onboardingFragmentViewModel.getUserAuthId().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<String>() {
            @Override
            public void onEvent(String s) {
                if(s==null || s.isEmpty()){

                    onboardingFragmentViewModel.setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_splashScreenFragment_to_onboardingFragment));
                }else{

                    SplashScreenFragmentDirections.ActionSplashScreenFragmentToDashboardFragment action =
                            SplashScreenFragmentDirections.actionSplashScreenFragmentToDashboardFragment(s);

                    onboardingFragmentViewModel.setNavigationCommandMutableLiveData(new NavigationCommand.NavigationAction(action));

                }
            }
        }));

        onboardingFragmentViewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(SplashScreenFragment.this).navigate(navDirections);


                }else if(navigationCommand instanceof NavigationCommand.NavigationId){
                    Log.d(TAG, "onChanged: command is id");
                    int navigationId = ((NavigationCommand.NavigationId) navigationCommand).getNavigationId();

                    if(navigationId== 0)
                        NavHostFragment.findNavController(SplashScreenFragment.this).popBackStack();
                    else
                        NavHostFragment.findNavController(SplashScreenFragment.this).navigate(navigationId);

                }
            }
        }));

        return fragmentSplashScreenBinding.getRoot();

    }

}
