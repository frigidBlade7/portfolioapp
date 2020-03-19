package com.codedevtech.portfolioapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.databinding.FragmentShowProfileBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.fragments.DashboardFragment;
import com.codedevtech.portfolioapp.viewmodels.DashboardFragmentViewModel;
import com.codedevtech.portfolioapp.viewmodels.ShowProfileFragmentViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProfileFragment extends BottomSheetDialogFragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ShowProfileFragmentViewModel showProfileFragmentViewModel;

    private String userAuthId;

    public ShowProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userAuthId = ShowProfileFragmentArgs.fromBundle(getArguments()).getUserId();
        showProfileFragmentViewModel.setFolioUserLiveData(userAuthId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentShowProfileBinding showProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_profile, container ,false);
        showProfileFragmentViewModel = new ViewModelProvider(this, viewModelFactory).get(ShowProfileFragmentViewModel.class);

        showProfileBinding.setViewModel(showProfileFragmentViewModel);
        showProfileBinding.setLifecycleOwner(this.getViewLifecycleOwner());



        return showProfileBinding.getRoot();
    }
}
