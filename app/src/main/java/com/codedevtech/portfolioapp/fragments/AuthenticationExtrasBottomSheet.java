package com.codedevtech.portfolioapp.fragments;


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

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentAuthenticationExtrasBottomSheetBinding;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.viewmodels.RegistrationFragmentViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthenticationExtrasBottomSheet extends BottomSheetDialogFragment {

    private static final String TAG = "AuthenticationExtrasBot";

    public AuthenticationExtrasBottomSheet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentAuthenticationExtrasBottomSheetBinding authenticationExtrasBottomSheetBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_authentication_extras_bottom_sheet, container, false);


        RegistrationFragmentViewModel registrationFragmentViewModel = ViewModelProviders
                .of(this).get(RegistrationFragmentViewModel.class);

        authenticationExtrasBottomSheetBinding.setViewmodel(registrationFragmentViewModel);

        registrationFragmentViewModel.getDestinationId().observe(this, new Observer<Event<Integer>>() {
            @Override
            public void onChanged(Event<Integer> integerEvent) {

                if(integerEvent.consume()==null)
                    return;

                Log.d(TAG, "onChanged: "+integerEvent.peek());

                if(integerEvent.peek()== 0)
                    NavHostFragment.findNavController(AuthenticationExtrasBottomSheet.this).popBackStack();
                else
                    NavHostFragment.findNavController(AuthenticationExtrasBottomSheet.this).navigate(integerEvent.peek());
            }
        });

        return authenticationExtrasBottomSheetBinding.getRoot();
    }

}
