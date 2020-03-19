package com.codedevtech.portfolioapp.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.databinding.FragmentProfileBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.navigation.EventListener;
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

        dashboardFragmentViewModel = ViewModelProviders.of(getParentFragment().getParentFragment(), viewModelFactory).get(DashboardFragmentViewModel.class);
        profileFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileFragmentViewModel.class);

        fragmentProfileBinding.setViewmodel(profileFragmentViewModel);
        fragmentProfileBinding.setDashboardviewmodel(dashboardFragmentViewModel);

        fragmentProfileBinding.setLifecycleOwner(this.getViewLifecycleOwner());


        profileFragmentViewModel.getShareLink().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<String>() {
            @Override
            public void onEvent(String s) {
                ShareCompat.IntentBuilder shareBuilder = ShareCompat.IntentBuilder.from(getActivity());
                shareBuilder.setChooserTitle(R.string.share_profile)
                        .setSubject(getString(R.string.share_profile))
                        .setText(getString(R.string.hey_check_out)+s)
                        .setType("text/plain");

                Log.d(TAG, "onChanged: "+s);

                Intent intent = shareBuilder.createChooserIntent();
                if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(intent);
            }
        }));



        profileFragmentViewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(ProfileFragment.this).navigate(navDirections);


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
                        profileFragmentViewModel.setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_global_loadingDialog));
                        profileFragmentViewModel.generateLink(dashboardFragmentViewModel.getFolioUserLiveData().getValue().data);
                        break;


                    case R.id.edit_profile:
                        DashboardFragmentDirections.ActionDashboardFragmentToEditProfileFragment action =
                                DashboardFragmentDirections.actionDashboardFragmentToEditProfileFragment(dashboardFragmentViewModel.getFolioUserLiveData().getValue().data,
                                        dashboardFragmentViewModel.getUserAuthId());

                        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(action);
/*                        DashboardFragmentDirections action =
                                ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(dashboardFragmentViewModel.getFolioUserLiveData().getValue().data,
                                        dashboardFragmentViewModel.getUserAuthId());

                        NavHostFragment.findNavController(ProfileFragment.this).navigate(action);*/
                        break;


                }
                return false;
            }
        });

        return fragmentProfileBinding.getRoot();


    }

}
