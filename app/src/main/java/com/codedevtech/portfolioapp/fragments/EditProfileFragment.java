package com.codedevtech.portfolioapp.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.commands.SnackbarCommand;
import com.codedevtech.portfolioapp.databinding.FragmentEditProfileBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.models.FolioUser;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.codedevtech.portfolioapp.viewmodels.CompleteProfileViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import javax.inject.Inject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment implements Injectable {

    private static final String TAG = "EditProfileFragment";
    private static final int RC_STORAGE = 45;
    private String userAuthenticationId;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CompleteProfileViewModel completeProfileViewModel;
    private FolioUser folioUser;
    private FragmentEditProfileBinding fragmentEditProfileBinding;


    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userAuthenticationId = EditProfileFragmentArgs.fromBundle(getArguments()).getUserAuthProviderId();
        folioUser = EditProfileFragmentArgs.fromBundle(getArguments()).getUser();
        Log.d(TAG, "onViewCreated: "+userAuthenticationId);

        completeProfileViewModel.setUserAuthProviderId(userAuthenticationId);
        completeProfileViewModel.fillUserFields(folioUser);

        populateRoles();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        fragmentEditProfileBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_edit_profile, container, false);

        fragmentEditProfileBinding.setLifecycleOwner(this.getViewLifecycleOwner());

        completeProfileViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CompleteProfileViewModel.class);

        fragmentEditProfileBinding.setViewmodel(completeProfileViewModel);


        completeProfileViewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(EditProfileFragment.this).navigate(navDirections);


                }else if(navigationCommand instanceof NavigationCommand.NavigationId){
                    Log.d(TAG, "onChanged: command is id");
                    int navigationId = ((NavigationCommand.NavigationId) navigationCommand).getNavigationId();

                    if(navigationId== 0)
                        NavHostFragment.findNavController(EditProfileFragment.this).popBackStack();
                    else
                        NavHostFragment.findNavController(EditProfileFragment.this).navigate(navigationId);

                }
            }
        }));

        completeProfileViewModel.getSnackbarCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<SnackbarCommand>() {
            @Override
            public void onEvent(SnackbarCommand snackbarCommand) {
                String s;

                if(snackbarCommand instanceof SnackbarCommand.SnackbarId){
                    s = getString(((SnackbarCommand.SnackbarId) snackbarCommand).getSnackbarId());

                }else if(snackbarCommand instanceof SnackbarCommand.SnackbarString){
                    s = ((SnackbarCommand.SnackbarString) snackbarCommand).getSnackbarString();
                }else{
                    return;
                }

                final Snackbar snackbar = Snackbar.make(fragmentEditProfileBinding.getRoot(),s , Snackbar.LENGTH_LONG);

                snackbar.setAction(R.string.okay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                snackbar.show();
            }
        }));

        fragmentEditProfileBinding.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult(completeProfileViewModel.getPickImageChooserIntent(), IMAGE_PICKER_CODE);
                methodRequiresPermission();
            }
        });

        fragmentEditProfileBinding.tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodRequiresPermission();
            }
        });



        return fragmentEditProfileBinding.getRoot();
    }

    @AfterPermissionGranted(RC_STORAGE)
    private void methodRequiresPermission() {

        //CropImage.getPickImageResultUri(getContext(), CropImage.getPickImageChooserIntent(getContext(),"hi",true,true));

        CropImage.startPickImageActivity(getActivity(), this);

       /* String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(EditProfileFragment.this.getContext(), perms)) {
            // Already have permission, do the thing
            // ...
            Log.d(TAG, "methodRequiresPermission: given ");
            //NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_editProfileFragment_to_updateProfilePhotoFragment);

            CropImage.startPickImageActivity(getContext(), EditProfileFragment.this);





        } else {
            // Do not have permissions, request them now
            Log.d(TAG, "methodRequiresPermission: requesting ");

            EasyPermissions.requestPermissions(EditProfileFragment.this, getString(R.string.permissions_rationale),
                    RC_STORAGE, perms);
        }
*/
    }

    private void populateRoles() {
        if(folioUser!=null){
            if(folioUser.getRoleFlags().contains(getString(R.string.artisan)))
                fragmentEditProfileBinding.artisan.setChecked(true);


            if(folioUser.getRoleFlags().contains(getString(R.string.enthusiast)))
                fragmentEditProfileBinding.enthusiast.setChecked(true);


            if(folioUser.getRoleFlags().contains(getString(R.string.photographer)))
                fragmentEditProfileBinding.photographer.setChecked(true);


            if(folioUser.getRoleFlags().contains(getString(R.string.developer)))
                fragmentEditProfileBinding.developer.setChecked(true);


            if(folioUser.getRoleFlags().contains(getString(R.string.illustrator)))
                fragmentEditProfileBinding.illustrator.setChecked(true);


            if(folioUser.getRoleFlags().contains(getString(R.string.designer)))
                fragmentEditProfileBinding.designer.setChecked(true);


            if(folioUser.getRoleFlags().contains(getString(R.string.recruiter)))
                fragmentEditProfileBinding.recruiter.setChecked(true);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {

            if(data.getData()!=null)
                CropImage.activity(data.getData())
                        .start(getContext(), this);

            else

                CropImage.activity(CropImage.getCaptureImageOutputUri(getContext()))
                        .start(getContext(), this);





        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                //update with cropped uri
                Glide.with(getContext()).load(resultUri).into(fragmentEditProfileBinding.profilePhoto);
                completeProfileViewModel.updateProfilePhoto(resultUri);



            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                completeProfileViewModel.setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(error.getLocalizedMessage()));
            }
        }
    }
}
