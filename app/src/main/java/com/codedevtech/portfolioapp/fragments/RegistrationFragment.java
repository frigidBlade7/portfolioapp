package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentRegistrationBinding;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.viewmodels.RegistrationFragmentViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    private static final String TAG = "AuthenticationFragment";

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentRegistrationBinding fragmentRegistrationBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_registration, container, false);

        RegistrationFragmentViewModel registrationFragmentViewModel = ViewModelProviders
                .of(this).get(RegistrationFragmentViewModel.class);

        fragmentRegistrationBinding.setViewmodel(registrationFragmentViewModel);

        registrationFragmentViewModel.getDestinationId().observe(this, new Observer<Event<Integer>>() {
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
