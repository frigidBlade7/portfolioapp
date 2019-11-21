package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentRegistrationBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.models.NavigationCommand;
import com.codedevtech.portfolioapp.models.SnackbarCommand;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.codedevtech.portfolioapp.viewmodels.RegistrationFragmentViewModel;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment implements Injectable {

    private static final String TAG = "AuthenticationFragment";

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final FragmentRegistrationBinding fragmentRegistrationBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_registration, container, false);

        final RegistrationFragmentViewModel registrationFragmentViewModel = ViewModelProviders
                .of(this, viewModelFactory).get(RegistrationFragmentViewModel.class);

        fragmentRegistrationBinding.setViewmodel(registrationFragmentViewModel);

        fragmentRegistrationBinding.setLifecycleOwner(this.getViewLifecycleOwner());

        registrationFragmentViewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(RegistrationFragment.this).navigate(navDirections);


                }else if(navigationCommand instanceof NavigationCommand.NavigationId){
                    Log.d(TAG, "onChanged: command is id");
                    int navigationId = ((NavigationCommand.NavigationId) navigationCommand).getNavigationId();

                    if(navigationId== 0)
                        NavHostFragment.findNavController(RegistrationFragment.this).popBackStack();
                    else
                        NavHostFragment.findNavController(RegistrationFragment.this).navigate(navigationId);

                }
            }
        }));


        registrationFragmentViewModel.getSnackbarCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<SnackbarCommand>() {
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

                final Snackbar snackbar = Snackbar.make(fragmentRegistrationBinding.getRoot(),s , Snackbar.LENGTH_LONG);

                snackbar.setAction(R.string.okay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                snackbar.show();
            }
        }));

        return fragmentRegistrationBinding.getRoot();

    }

}
