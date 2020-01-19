package com.codedevtech.portfolioapp.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentNewPostBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.viewmodels.NewPostFragmentViewModel;

import javax.inject.Inject;

public class NewPostFragment extends Fragment implements Injectable {
    private static final String TAG = "NewPostFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public NewPostFragment() {
        // Required empty public constructor


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentNewPostBinding fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_post, container, false);
        NewPostFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewPostFragmentViewModel.class);

        fragmentBinding.setLifecycleOwner(this.getViewLifecycleOwner());
        fragmentBinding.setViewmodel(viewModel);

        return fragmentBinding.getRoot();



    }


}
