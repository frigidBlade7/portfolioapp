package com.codedevtech.portfolioapp.fragments;


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

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentAuthenticationBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.commands.SnackbarCommand;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.codedevtech.portfolioapp.viewmodels.AuthenticationFragmentViewModel;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthenticationFragment extends Fragment implements Injectable{

    private static final String TAG = "AuthenticationFragment";


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    
    public AuthenticationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final FragmentAuthenticationBinding fragmentAuthenticationBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_authentication, container, false);
        FragmentAuthenticationBinding fragmentAuthenticationBinding1 = FragmentAuthenticationBinding.inflate(getLayoutInflater());

        final AuthenticationFragmentViewModel authenticationFragmentViewModel = new ViewModelProvider(this).get(AuthenticationFragmentViewModel.class);

        fragmentAuthenticationBinding.setViewmodel(authenticationFragmentViewModel);

        fragmentAuthenticationBinding.setLifecycleOwner(this.getViewLifecycleOwner());

/*        authenticationFragmentViewModel.getEmailMutableLiveData().observe(this.getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "onChanged: "+s);
            }
        });*/

        authenticationFragmentViewModel.getSnackbarCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<SnackbarCommand>() {
            @Override
            public void onEvent(SnackbarCommand snackbarCommand) {
                String s;

                if(snackbarCommand instanceof SnackbarCommand.SnackbarId){
                    s = getString(((SnackbarCommand.SnackbarId) snackbarCommand).getSnackbarId());

                }else if(snackbarCommand instanceof SnackbarCommand.SnackbarString){
                    s = ((SnackbarCommand.SnackbarString) snackbarCommand).getSnackbarString();
                }
                else {
                    return;
                }

                final Snackbar snackbar = Snackbar.make(fragmentAuthenticationBinding.getRoot(), s , Snackbar.LENGTH_LONG);

                snackbar.setAction(R.string.okay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                snackbar.show();
            }
        }));

        authenticationFragmentViewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(AuthenticationFragment.this).navigate(navDirections);


                }else if(navigationCommand instanceof NavigationCommand.NavigationId){
                    Log.d(TAG, "onChanged: command is id");
                    int navigationId = ((NavigationCommand.NavigationId) navigationCommand).getNavigationId();

                    Utility.hideSoftKeyboard(fragmentAuthenticationBinding.password, getActivity());

                    if(navigationId== 0)
                        NavHostFragment.findNavController(AuthenticationFragment.this).popBackStack();
                    else
                        NavHostFragment.findNavController(AuthenticationFragment.this).navigate(navigationId);

                }
            }
        }));


        return fragmentAuthenticationBinding.getRoot();

    }

}
