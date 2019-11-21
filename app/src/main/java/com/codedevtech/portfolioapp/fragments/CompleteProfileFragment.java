package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentCompleteProfileBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.models.CompleteProfileViewModel;
import com.codedevtech.portfolioapp.models.NavigationCommand;
import com.codedevtech.portfolioapp.models.SnackbarCommand;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteProfileFragment extends Fragment implements Injectable {

    private static final String TAG = "CompleteProfileFragment";
    private String userAuthenticationId;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    public CompleteProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userAuthenticationId = CompleteProfileFragmentArgs.fromBundle(getArguments()).getUserAuthProviderId();
        Log.d(TAG, "onViewCreated: "+userAuthenticationId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final FragmentCompleteProfileBinding fragmentCompleteProfileBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_complete_profile, container, false);

        fragmentCompleteProfileBinding.setLifecycleOwner(this.getViewLifecycleOwner());

        CompleteProfileViewModel completeProfileViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CompleteProfileViewModel.class);


        fragmentCompleteProfileBinding.setViewmodel(completeProfileViewModel);

        completeProfileViewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(CompleteProfileFragment.this).navigate(navDirections);


                }else if(navigationCommand instanceof NavigationCommand.NavigationId){
                    Log.d(TAG, "onChanged: command is id");
                    int navigationId = ((NavigationCommand.NavigationId) navigationCommand).getNavigationId();

                    if(navigationId== 0)
                        NavHostFragment.findNavController(CompleteProfileFragment.this).popBackStack();
                    else
                        NavHostFragment.findNavController(CompleteProfileFragment.this).navigate(navigationId);

                }
            }
        }));

        completeProfileViewModel.getSnackbarCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<SnackbarCommand>() {
            @Override
            public void onEvent(SnackbarCommand snackbarCommand) {
                String s;

                if(snackbarCommand instanceof SnackbarCommand.SnackbarId){
                    s = getString(((SnackbarCommand.SnackbarId) snackbarCommand).getSnackbarId());

                }else if(snackbarCommand instanceof SnackbarCommand.SnackbarString){
                    s = ((SnackbarCommand.SnackbarString) snackbarCommand).getSnackbarString();
                }else{
                    s = "";
                }

                final Snackbar snackbar = Snackbar.make(fragmentCompleteProfileBinding.getRoot(),s , Snackbar.LENGTH_LONG);

                snackbar.setAction(R.string.okay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                snackbar.show();
            }
        }));


        return fragmentCompleteProfileBinding.getRoot();
    }

}
