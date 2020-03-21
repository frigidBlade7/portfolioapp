package com.codedevtech.portfolioapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.adapters.pagination.FireStoreFeedDocumentPagingAdapter;
import com.codedevtech.portfolioapp.databinding.FragmentShowProfileBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.models.FeedPost;
import com.codedevtech.portfolioapp.viewmodels.ShowProfileFragmentViewModel;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProfileFragment extends BottomSheetDialogFragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ShowProfileFragmentViewModel showProfileFragmentViewModel;

    @Inject
    PagedList.Config config;



    private FirestorePagingOptions<FeedPost> options;
    private FireStoreFeedDocumentPagingAdapter fireStoreFeedDocumentPagingAdapter;
    private String userAuthId;
    private FragmentShowProfileBinding showProfileBinding;

    public ShowProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userAuthId = ShowProfileFragmentArgs.fromBundle(getArguments()).getUserId();
        showProfileFragmentViewModel.setFolioUserLiveData(userAuthId);


        showProfileFragmentViewModel.setQueryLiveData(userAuthId);




        //create options object
        options = new FirestorePagingOptions.Builder<FeedPost>()
                .setLifecycleOwner(ShowProfileFragment.this)
                .setQuery(showProfileFragmentViewModel.getUserFeedQuery(), config, FeedPost.class)
                .build();



        //create document adapter with options
        fireStoreFeedDocumentPagingAdapter = new FireStoreFeedDocumentPagingAdapter(options, this, userAuthId);

        showProfileBinding.list.setAdapter(fireStoreFeedDocumentPagingAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        showProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_profile, container ,false);
        showProfileFragmentViewModel = new ViewModelProvider(this, viewModelFactory).get(ShowProfileFragmentViewModel.class);

        showProfileBinding.setViewModel(showProfileFragmentViewModel);
        showProfileBinding.setLifecycleOwner(this.getViewLifecycleOwner());


        return showProfileBinding.getRoot();
    }
}
