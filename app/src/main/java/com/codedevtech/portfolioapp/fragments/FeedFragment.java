package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentFeedBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.models.FeedDocument;
import com.codedevtech.portfolioapp.models.FeedPost;
import com.codedevtech.portfolioapp.viewmodels.FeedFragmentViewModel;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements Injectable {


    @Inject
    ViewModelProvider.Factory viewmodelFactory;

    @Inject
    PagedList.Config config;
    private FirestorePagingOptions<FeedDocument> options;

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFeedBinding fragmentFeedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        FeedFragmentViewModel feedFragmentViewModel = ViewModelProviders.of(this, viewmodelFactory).get(FeedFragmentViewModel.class);
        fragmentFeedBinding.setViewmodel(feedFragmentViewModel);


        feedFragmentViewModel.getQueryLiveData().observe(getViewLifecycleOwner(), new Observer<Query>() {
            @Override
            public void onChanged(Query query) {
                options = new FirestorePagingOptions.Builder<FeedDocument>()
                        .setLifecycleOwner(FeedFragment.this)
                        .setQuery(query, config, FeedDocument.class)
                        .build();
            }
        });
        fragmentFeedBinding.setLifecycleOwner(this);

        return fragmentFeedBinding.getRoot();
    }

}
