package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentAuthenticationBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.navigation.Event;
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

        final AuthenticationFragmentViewModel authenticationFragmentViewModel = ViewModelProviders
                .of(this, viewModelFactory).get(AuthenticationFragmentViewModel.class);

        fragmentAuthenticationBinding.setViewmodel(authenticationFragmentViewModel);

        authenticationFragmentViewModel.getDestinationId().observe(this.getViewLifecycleOwner(), new Observer<Event<Integer>>() {
            @Override
            public void onChanged(Event<Integer> integerEvent) {

                if(integerEvent.consume()==null)
                    return;

                Log.d(TAG, "onChanged: "+integerEvent.peek());
                if(integerEvent.peek()== 0)
                    NavHostFragment.findNavController(AuthenticationFragment.this).popBackStack();
                else
                    NavHostFragment.findNavController(AuthenticationFragment.this).navigate(integerEvent.peek());
            }
        });

        authenticationFragmentViewModel.getSnackbarMessageId().observe(this.getViewLifecycleOwner(), new Observer<Event<Integer>>() {
            @Override
            public void onChanged(Event<Integer> integerEvent) {
                if(integerEvent.consume()==null)
                    return;

                final Snackbar snackbar = Snackbar.make(fragmentAuthenticationBinding.getRoot(), getString(integerEvent.peek()), Snackbar.LENGTH_LONG);

                snackbar.setAction(R.string.okay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                snackbar.show();
            }
        });

        authenticationFragmentViewModel.getSnackbarMessage().observe(this.getViewLifecycleOwner(), new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                if(stringEvent.consume()==null)
                    return;

                final Snackbar snackbar = Snackbar.make(fragmentAuthenticationBinding.getRoot(), stringEvent.peek(), Snackbar.LENGTH_LONG);

                snackbar.setAction(R.string.okay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                snackbar.show();
            }
        });



        return fragmentAuthenticationBinding.getRoot();

    }

}
