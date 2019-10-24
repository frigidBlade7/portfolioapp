package com.codedevtech.portfolioapp;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.databinding.FragmentAuthenticationExtrasBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AuthenticationExtrasBottomSheet extends BottomSheetDialogFragment {


    public AuthenticationExtrasBottomSheet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentAuthenticationExtrasBottomSheetBinding authenticationExtrasBottomSheetBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_authentication_extras_bottom_sheet, container, false);



        return authenticationExtrasBottomSheetBinding.getRoot();
    }

}
