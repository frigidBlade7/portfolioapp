package com.codedevtech.portfolioapp;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.databinding.FragmentAuthenticationBinding;
import com.codedevtech.portfolioapp.fragments.OnboardingFragment;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.viewmodels.AuthenticationFragmentViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthenticationFragment extends Fragment {

    private static final String TAG = "AuthenticationFragment";

    public AuthenticationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentAuthenticationBinding fragmentAuthenticationBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_authentication, container, false);

        AuthenticationFragmentViewModel authenticationFragmentViewModel = ViewModelProviders
                .of(this).get(AuthenticationFragmentViewModel.class);

        fragmentAuthenticationBinding.setViewmodel(authenticationFragmentViewModel);

        authenticationFragmentViewModel.getDestinationId().observe(this, new Observer<Event<Integer>>() {
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

        return fragmentAuthenticationBinding.getRoot();

    }

}