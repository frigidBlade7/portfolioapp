package com.codedevtech.portfolioapp.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentAuthenticationExtrasBottomSheetBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.di.models.FactoryViewModel;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.utilities.RequestCodeUtilities;
import com.codedevtech.portfolioapp.viewmodels.AuthenticationFragmentViewModel;
import com.codedevtech.portfolioapp.viewmodels.RegistrationFragmentViewModel;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthenticationExtrasBottomSheet extends BottomSheetDialogFragment implements Injectable {


    private static final String TAG = "AuthenticationExtrasBot";
    private AuthenticationFragmentViewModel authenticationFragmentViewModel;


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public AuthenticationExtrasBottomSheet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentAuthenticationExtrasBottomSheetBinding authenticationExtrasBottomSheetBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_authentication_extras_bottom_sheet, container, false);


        authenticationFragmentViewModel = ViewModelProviders
                .of(this, viewModelFactory).get(AuthenticationFragmentViewModel.class);

        authenticationExtrasBottomSheetBinding.setViewmodel(authenticationFragmentViewModel);

        authenticationFragmentViewModel.getDestinationId().observe(this.getViewLifecycleOwner(), new Observer<Event<Integer>>() {
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

        authenticationFragmentViewModel.getSignInIntent().observe(this.getViewLifecycleOwner(), new Observer<Event<Intent>>() {
            @Override
            public void onChanged(Event<Intent> intentEvent) {
                if(intentEvent.consume()==null)
                    return;

                startActivityForResult(intentEvent.peek(), RequestCodeUtilities.RC_SIGN_IN);

            }
        });

        authenticationFragmentViewModel.getFacebookLoginParameter().observe(this.getViewLifecycleOwner(), new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                if(stringEvent.consume()==null)
                    return;

                LoginManager.getInstance().logInWithReadPermissions(AuthenticationExtrasBottomSheet.this, Arrays.asList(stringEvent.peek()));
            }
        });


        return authenticationExtrasBottomSheetBinding.getRoot();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        authenticationFragmentViewModel.executeOnActivityResultCalled(requestCode,resultCode, data);

    }

}
