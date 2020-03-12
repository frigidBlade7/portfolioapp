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
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.databinding.FragmentNewPostBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
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


        fragmentBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        dashboardFragmentViewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(navDirections);


                }else if(navigationCommand instanceof NavigationCommand.NavigationId){
                    Log.d(TAG, "onChanged: command is id");
                    int navigationId = ((NavigationCommand.NavigationId) navigationCommand).getNavigationId();

                    if(navigationId== 0)
                        NavHostFragment.findNavController(getParentFragment().getParentFragment()).popBackStack();
                    else
                        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(navigationId);

                }
            }
        }));



        return fragmentBinding.getRoot();



    }


}
