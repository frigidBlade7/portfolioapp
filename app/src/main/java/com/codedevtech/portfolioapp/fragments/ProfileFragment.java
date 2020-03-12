package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentProfileBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.codedevtech.portfolioapp.repositories.Resource;
import com.codedevtech.portfolioapp.utilities.ResourceStatus;
import com.codedevtech.portfolioapp.viewmodels.DashboardFragmentViewModel;
import com.codedevtech.portfolioapp.viewmodels.ProfileFragmentViewModel;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements Injectable {


    private static final String TAG = "ProfileFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private DashboardFragmentViewModel dashboardFragmentViewModel;
    private ProfileFragmentViewModel profileFragmentViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentProfileBinding fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        dashboardFragmentViewModel = ViewModelProviders.of(getParentFragment().getParentFragment().getParentFragment(), viewModelFactory).get(DashboardFragmentViewModel.class);
        profileFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileFragmentViewModel.class);

        fragmentProfileBinding.setViewmodel(profileFragmentViewModel);
        fragmentProfileBinding.setDashboardviewmodel(dashboardFragmentViewModel);

        fragmentProfileBinding.setLifecycleOwner(this.getViewLifecycleOwner());


        dashboardFragmentViewModel.getFolioUserLiveData().observe(this.getViewLifecycleOwner(), new Observer<Resource<FolioUser>>() {
            @Override
            public void onChanged(Resource<FolioUser> folioUserResource) {
                if(folioUserResource.status.equals(ResourceStatus.ERROR)) {
                    Log.d(TAG, "onChanged: "+"error");

                    return;
                }
                else if(folioUserResource.status.equals(ResourceStatus.SUCCESS))
                    Log.d(TAG, "onChanged: "+folioUserResource.data.getFirstName());
            }
        });


        //setHasOptionsMenu(true);
        fragmentProfileBinding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.share_profile:

                        break;


                    case R.id.edit_profile:

                        break;


                }
                return false;
            }
        });

        return fragmentProfileBinding.getRoot();


    }

}
