package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentCompleteProfileBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteProfileFragment extends Fragment {


    public CompleteProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentCompleteProfileBinding fragmentCompleteProfileBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_complete_profile, container, false);


        return fragmentCompleteProfileBinding.getRoot();
    }

}
