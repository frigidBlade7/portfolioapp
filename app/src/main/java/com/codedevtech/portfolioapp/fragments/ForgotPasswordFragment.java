package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentForgotPasswordBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPasswordFragment extends BottomSheetDialogFragment {


    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        FragmentForgotPasswordBinding forgotPasswordBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_forgot_password, container, false);


        return forgotPasswordBinding.getRoot();
    }

}
