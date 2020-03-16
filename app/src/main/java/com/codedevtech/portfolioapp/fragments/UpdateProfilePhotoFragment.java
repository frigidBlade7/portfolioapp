package com.codedevtech.portfolioapp.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.adapters.recycler_view_adapters.BottomSheetOptionAdapter;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.commands.SnackbarCommand;
import com.codedevtech.portfolioapp.databinding.FragmentUpdateProfilePhotoBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.interfaces.listeners.BottomSheetOptionListener;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.codedevtech.portfolioapp.utilities.Utility;
import com.codedevtech.portfolioapp.viewmodels.ProfileFragmentViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class UpdateProfilePhotoFragment extends BottomSheetDialogFragment implements Injectable, BottomSheetOptionListener {

    public static final int RC_GALLERY = 132;
    public static final int RC_CAMERA = 187;
    private static final String TAG = "UpdateProfilePhotoFragm";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FragmentUpdateProfilePhotoBinding fragmentBinding;
    private ProfileFragmentViewModel viewModel;

    public UpdateProfilePhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_profile_photo, container, false);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ProfileFragmentViewModel.class);

        fragmentBinding.setLifecycleOwner(this.getViewLifecycleOwner());
        fragmentBinding.setViewmodel(viewModel);

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(R.drawable.gallery, R.drawable.camera, R.drawable.delete));
        BottomSheetOptionAdapter bottomSheetOptionAdapter = new BottomSheetOptionAdapter(Arrays.asList(getResources().getStringArray(R.array.photo_options)),arrayList, this);
        fragmentBinding.recyclerView.setAdapter(bottomSheetOptionAdapter);
        bottomSheetOptionAdapter.notifyDataSetChanged();


        viewModel.getSnackbarCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<SnackbarCommand>() {
            @Override
            public void onEvent(SnackbarCommand snackbarCommand) {
                String s;

                if(snackbarCommand instanceof SnackbarCommand.SnackbarId){
                    s = getString(((SnackbarCommand.SnackbarId) snackbarCommand).getSnackbarId());

                }else if(snackbarCommand instanceof SnackbarCommand.SnackbarString){
                    s = ((SnackbarCommand.SnackbarString) snackbarCommand).getSnackbarString();
                }
                else {
                    return;
                }

                final Snackbar snackbar = Snackbar.make(getParentFragment().getView(), s , Snackbar.LENGTH_LONG);

                snackbar.setAction(R.string.okay, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                snackbar.show();
            }
        }));
        //find a way to globalize this navigation
        viewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(UpdateProfilePhotoFragment.this).navigate(navDirections);


                }else if(navigationCommand instanceof NavigationCommand.NavigationId){
                    Log.d(TAG, "onChanged: command is id");
                    int navigationId = ((NavigationCommand.NavigationId) navigationCommand).getNavigationId();

                    if(navigationId== 0)
                        NavHostFragment.findNavController(UpdateProfilePhotoFragment.this).popBackStack();
                    else
                        NavHostFragment.findNavController(UpdateProfilePhotoFragment.this).navigate(navigationId);

                }
            }
        }));


        return fragmentBinding.getRoot();


    }


    @Override
    public void onOptionClicked(int position) {

        Intent intent;

        switch (position){
            case 0:
                Log.d(TAG, "onOptionClicked: 0");

                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(Intent.createChooser(intent, "Choose Picture"), RC_GALLERY);
                }
                break;


            case 1:
                Log.d(TAG, "onOptionClicked: 1");

                File photoFile;

                try {
                    photoFile = Utility.createImageFile(getContext());
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    viewModel.setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(ex.getLocalizedMessage()));
                    return;

                }

                if(photoFile!=null){
                    Uri photoURI = FileProvider.getUriForFile(getContext(),
                            "com.codedevtech.easyfindgh.fileprovider",
                            photoFile);

                    //todo viewModel.getUserRepositoryService().setUri(photoURI);

                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(intent, RC_CAMERA);
                    }
                }

/*
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, RC_CAMERA);
                }*/
                break;


            case 2:
                Log.d(TAG, "onOptionClicked: 2");
                viewModel.setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext()/*, R.style.AlertDialog*/);
                alert.setMessage(getString(R.string.are_you_sure_delete_profile))
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.delete_photo, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //todo viewModel.deletePhoto();
                            }
                        }).show();
                break;

                default:


        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //todo FirebaseUserRepositoryService userRepositoryService = new FirebaseUserRepositoryService("users");

        if (requestCode == RC_CAMERA && resultCode == RESULT_OK) {

/*            Bitmap thumbnail = data.getParcelableExtra("data");
            userRepositoryService.setBitmap(thumbnail);*/

            //todo CropImage.activity(viewModel.getUserRepositoryService().getUri()).start(getContext(), this);

        }

        else if (requestCode == RC_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            //todo viewModel.getUserRepositoryService().setUri(uri);

            //todo CropImage.activity(viewModel.getUserRepositoryService().getUri()).start(getContext(), this);
            //Uri fullPhotoUri = data.getData();
            // Do work with photo saved at fullPhotoUri

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                //update with cropped uri
                //todo viewModel.getUserRepositoryService().setUri(resultUri);
                //todo viewModel.updateProfilePhoto();


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                viewModel.setSnackbarCommandMutableLiveData(new SnackbarCommand.SnackbarString(error.getLocalizedMessage()));
            }
        }



        /*viewModel.updateProfilePhoto(viewModel.getUserRepositoryService());*/




    }
}
