package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentExploreBinding;
import com.codedevtech.portfolioapp.viewmodels.DashboardFragmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FloatingActionButton newPost;


    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentExploreBinding fragmentExploreBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false);

        DashboardFragmentViewModel dashboardFragmentViewModel = ViewModelProviders.of(getParentFragment(), viewModelFactory).get(DashboardFragmentViewModel.class);


        fragmentExploreBinding.setDashboardViewModel(dashboardFragmentViewModel);

        return fragmentExploreBinding.getRoot();

    }

}
