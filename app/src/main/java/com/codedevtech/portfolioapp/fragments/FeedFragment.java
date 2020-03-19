package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.adapters.pagination.FireStoreFeedDocumentPagingAdapter;
import com.codedevtech.portfolioapp.commands.NavigationCommand;
import com.codedevtech.portfolioapp.databinding.FragmentFeedBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.interfaces.listeners.FeedListener;
import com.codedevtech.portfolioapp.models.FeedPost;
import com.codedevtech.portfolioapp.navigation.EventListener;
import com.codedevtech.portfolioapp.navigation.EventObserver;
import com.codedevtech.portfolioapp.viewmodels.DashboardFragmentViewModel;
import com.codedevtech.portfolioapp.viewmodels.FeedFragmentViewModel;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements Injectable, FeedListener {

    private static final String TAG = "FeedFragment";

    @Inject
    ViewModelProvider.Factory viewmodelFactory;

    @Inject
    PagedList.Config config;

    private FirestorePagingOptions<FeedPost> options;
    private FeedFragmentViewModel feedFragmentViewModel;
    private DashboardFragmentViewModel dashboardFragmentViewModel;
    private FireStoreFeedDocumentPagingAdapter fireStoreFeedDocumentPagingAdapter;
    private FloatingActionButton newPost;
    private FragmentFeedBinding fragmentFeedBinding;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        newPost.hide();

    }

    @Override
    public void onResume() {
        super.onResume();
        newPost.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFeedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        feedFragmentViewModel = ViewModelProviders.of(this, viewmodelFactory).get(FeedFragmentViewModel.class);
        dashboardFragmentViewModel = ViewModelProviders.of(getParentFragment().getParentFragment(), viewmodelFactory).get(DashboardFragmentViewModel.class);

        newPost = fragmentFeedBinding.newPostFAB;
        //add viewmodel for both dashboard and feed fragments
        fragmentFeedBinding.setViewmodel(feedFragmentViewModel);
        fragmentFeedBinding.setDashboardViewModel(dashboardFragmentViewModel);


        fragmentFeedBinding.setLifecycleOwner(this.getViewLifecycleOwner());


        feedFragmentViewModel.getNavigationCommandMutableLiveData().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<NavigationCommand>() {
            @Override
            public void onEvent(NavigationCommand navigationCommand) {
                if(navigationCommand instanceof NavigationCommand.NavigationAction){
                    Log.d(TAG, "onChanged: command is action");
                    NavDirections navDirections = ((NavigationCommand.NavigationAction) navigationCommand).getDirections();
                    NavHostFragment.findNavController(FeedFragment.this).navigate(navDirections);


                }else if(navigationCommand instanceof NavigationCommand.NavigationId){
                    Log.d(TAG, "onChanged: command is id");
                    int navigationId = ((NavigationCommand.NavigationId) navigationCommand).getNavigationId();

                    if(navigationId== 0)
                        NavHostFragment.findNavController(FeedFragment.this).popBackStack();
                    else
                        NavHostFragment.findNavController(FeedFragment.this).navigate(navigationId);

                }
            }
        }));

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
        //update userid for query
        feedFragmentViewModel.setQueryLiveData();




        //create options object
        options = new FirestorePagingOptions.Builder<FeedPost>()
                .setLifecycleOwner(FeedFragment.this)
                .setQuery(feedFragmentViewModel.getUserFeedQuery(), config, FeedPost.class)
                .build();



        //create document adapter with options
        fireStoreFeedDocumentPagingAdapter = new FireStoreFeedDocumentPagingAdapter(options, this, dashboardFragmentViewModel.getUserAuthId());

        fragmentFeedBinding.cardList.setAdapter(fireStoreFeedDocumentPagingAdapter);


        fireStoreFeedDocumentPagingAdapter.addLoadStateListener(new PagedList.LoadStateListener() {

            @Override
            public void onLoadStateChanged(@NonNull PagedList.LoadType type, @NonNull PagedList.LoadState state, @Nullable Throwable error) {
                //todo show the loaders
                Log.d(TAG, "onLoadStateChanged: "+state.name());
                fragmentFeedBinding.emptyStateLayout.setVisibility(View.GONE);

                switch (state){

                    case IDLE:
                        fragmentFeedBinding.cardListShim.startShimmer();
                        fragmentFeedBinding.cardListShim.setVisibility(View.VISIBLE);
                        /*fragmentFeedBinding.cardList.setVisibility(View.GONE);*/
                        //fragmentFeedBinding.cardListShim.startShimmer();
                        // The initial load has begun
                        // ...
                        fragmentFeedBinding.emptyStateLayout.setVisibility(View.GONE);



                    case LOADING:
                        // The adapter has started to load an additional page
                        // ...
                        /*fragmentFeedBinding.cardListShim.startShimmer();
                        fragmentFeedBinding.cardListShim.setVisibility(View.VISIBLE);*/
                        fragmentFeedBinding.emptyStateLayout.setVisibility(View.GONE);


                    case DONE:
                        // The previous load (either initial or additional) completed
                        // ...

                        if(fireStoreFeedDocumentPagingAdapter.getItemCount()>0)
                            fragmentFeedBinding.emptyStateLayout.setVisibility(View.GONE);

                        Log.d(TAG, "onLoadStateChanged: "+        fireStoreFeedDocumentPagingAdapter.getItemCount());

                        fragmentFeedBinding.cardListShim.stopShimmer();
                        fragmentFeedBinding.cardListShim.setVisibility(View.GONE);


                    case ERROR:
                        // The previous load (either initial or additional) failed. Call
                        // the retry() method in order to retry the load operation.
                }

            }
        });


/*        //todo find another implementation for this
        feedFragmentViewModel.getUserFeedQuery().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots.isEmpty()){
                    //todo show the user to some hey there make a new post stuff
                }else{
                    fragmentFeedBinding.cardListShim.stopShimmer();
                    fragmentFeedBinding.cardListShim.setVisibility(View.GONE);
                    fragmentFeedBinding.cardList.setVisibility(View.VISIBLE);
                }
            }
        });*/



        return fragmentFeedBinding.getRoot();
    }

    @Override
    public void onFeedProfilePhotoTapped(FeedPost feedPost) {
        if(feedPost.getUserId().equals(dashboardFragmentViewModel.getUserAuthId()))
            Toast.makeText(getContext(), "nate!", Toast.LENGTH_SHORT).show();
        else{
            DashboardFragmentDirections.ActionDashboardFragmentToShowProfileFragment action =
                    DashboardFragmentDirections.actionDashboardFragmentToShowProfileFragment(feedPost.getUserId());

            dashboardFragmentViewModel.setNavigationCommandMutableLiveData(new NavigationCommand.NavigationAction(action));
        }
    }

}
