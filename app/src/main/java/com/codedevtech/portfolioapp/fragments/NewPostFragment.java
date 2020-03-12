package com.codedevtech.portfolioapp.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentNewPostBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.service_implementations.GlideApp;
import com.codedevtech.portfolioapp.viewmodels.DashboardFragmentViewModel;
import com.codedevtech.portfolioapp.viewmodels.NewPostFragmentViewModel;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Inject;

public class NewPostFragment extends Fragment implements Injectable {
    private static final String TAG = "NewPostFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public NewPostFragment() {
        // Required empty public constructor


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentNewPostBinding fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_post, container, false);
        NewPostFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewPostFragmentViewModel.class);
        DashboardFragmentViewModel dashboardFragmentViewModel = ViewModelProviders.of(getParentFragment(), viewModelFactory).get(DashboardFragmentViewModel.class);

        fragmentBinding.setViewmodel(viewModel);
        fragmentBinding.setDashboardViewModel(dashboardFragmentViewModel);
        fragmentBinding.setLifecycleOwner(this.getViewLifecycleOwner());



        return fragmentBinding.getRoot();



    }


}
