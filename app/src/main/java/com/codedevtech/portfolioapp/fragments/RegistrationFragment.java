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
import androidx.navigation.fragment.NavHostFragment;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentRegistrationBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.viewmodels.RegistrationFragmentViewModel;

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

        FragmentRegistrationBinding fragmentRegistrationBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_registration, container, false);

        RegistrationFragmentViewModel registrationFragmentViewModel = ViewModelProviders
                .of(this, viewModelFactory).get(RegistrationFragmentViewModel.class);

        fragmentRegistrationBinding.setViewmodel(registrationFragmentViewModel);

        fragmentRegistrationBinding.setLifecycleOwner(this.getViewLifecycleOwner());

        registrationFragmentViewModel.getDestinationId().observe(this.getViewLifecycleOwner(), new Observer<Event<Integer>>() {
            @Override
            public void onChanged(Event<Integer> integerEvent) {

                if(integerEvent.consume()==null)
                    return;

                Log.d(TAG, "onChanged: "+integerEvent.peek());
                if(integerEvent.peek()== 0)
                    NavHostFragment.findNavController(RegistrationFragment.this).popBackStack();
                else
                    NavHostFragment.findNavController(RegistrationFragment.this).navigate(integerEvent.peek());
            }
        });

        return fragmentRegistrationBinding.getRoot();

    }

}
