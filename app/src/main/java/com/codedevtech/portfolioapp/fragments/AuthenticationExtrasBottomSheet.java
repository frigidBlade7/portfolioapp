package com.codedevtech.portfolioapp.fragments;


import android.content.Intent;
import android.os.Bundle;

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
import com.codedevtech.portfolioapp.databinding.FragmentAuthenticationExtrasBottomSheetBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.navigation.Event;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.codedevtech.portfolioapp.utilities.RequestCodeUtilities;
import com.codedevtech.portfolioapp.viewmodels.AuthenticationFragmentViewModel;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.List;

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

        authenticationFragmentViewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(AuthenticationExtrasBottomSheet.this).navigate(navDirections);


                }else if(navigationCommand instanceof NavigationCommand.NavigationId){
                    Log.d(TAG, "onChanged: command is id");
                    int navigationId = ((NavigationCommand.NavigationId) navigationCommand).getNavigationId();

                    if(navigationId== 0)
                        NavHostFragment.findNavController(AuthenticationExtrasBottomSheet.this).popBackStack();
                    else
                        NavHostFragment.findNavController(AuthenticationExtrasBottomSheet.this).navigate(navigationId);

                }
            }
        }));

        authenticationFragmentViewModel.getSignInIntent().observe(this.getViewLifecycleOwner(), new Observer<Event<Intent>>() {
            @Override
            public void onChanged(Event<Intent> intentEvent) {
                if(intentEvent.consume()==null)
                    return;

                startActivityForResult(intentEvent.peek(), RequestCodeUtilities.RC_SIGN_IN);

            }
        });

        authenticationFragmentViewModel.getFacebookLoginParameter().observe(this.getViewLifecycleOwner(), new Observer<Event<List<String>>>() {
            @Override
            public void onChanged(Event<List<String>> listStringEvent) {
                if(listStringEvent.consume()==null)
                    return;

                LoginManager.getInstance().logInWithReadPermissions(AuthenticationExtrasBottomSheet.this, listStringEvent.peek());
            }
        });

        authenticationFragmentViewModel.getoAuthProvider().observe(this.getViewLifecycleOwner(), new Observer<Event<OAuthProvider>>() {
            @Override
            public void onChanged(Event<OAuthProvider> oAuthProviderEvent) {
                if(oAuthProviderEvent.consume()==null)
                    return;

                authenticationFragmentViewModel.completeTwitterSignIn(FirebaseAuth.getInstance().startActivityForSignInWithProvider(AuthenticationExtrasBottomSheet.this.getActivity(), oAuthProviderEvent.peek()));
            }
        });

        return authenticationExtrasBottomSheetBinding.getRoot();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        authenticationFragmentViewModel.executeOnActivityResultCalled(requestCode,resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

}
