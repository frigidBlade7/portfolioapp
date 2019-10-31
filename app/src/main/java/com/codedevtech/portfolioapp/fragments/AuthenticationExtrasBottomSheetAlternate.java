package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentAuthenticationExtrasBottomSheetAlternateBinding;
import com.codedevtech.portfolioapp.databinding.FragmentAuthenticationExtrasBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthenticationExtrasBottomSheetAlternate extends BottomSheetDialogFragment {


    public AuthenticationExtrasBottomSheetAlternate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentAuthenticationExtrasBottomSheetAlternateBinding authenticationExtrasBottomSheetAlternateBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_authentication_extras_bottom_sheet_alternate, container, false);



        return authenticationExtrasBottomSheetAlternateBinding.getRoot();
    }

}
