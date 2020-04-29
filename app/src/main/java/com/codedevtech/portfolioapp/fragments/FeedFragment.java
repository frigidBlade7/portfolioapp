package com.codedevtech.portfolioapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codedevtech.portfolioapp.MainActivity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements Injectable, FeedListener {

    private static final String TAG = "FeedFragment";


    @Inject
    ViewModelProvider.Factory viewmodelFactory;
/*
    @Inject
    PagedList.Config config;*/

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: ");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentFeedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        feedFragmentViewModel = new ViewModelProvider(this).get(FeedFragmentViewModel.class);
        dashboardFragmentViewModel = ViewModelProviders.of(getParentFragment().getParentFragment().getParentFragment(), viewmodelFactory).get(DashboardFragmentViewModel.class);

        newPost = fragmentFeedBinding.newPostFAB;
        //add viewmodel for both dashboard and feed fragments
        fragmentFeedBinding.setViewmodel(feedFragmentViewModel);
        fragmentFeedBinding.setDashboardViewModel(dashboardFragmentViewModel);


        Log.d(TAG, "onCreateView: ");
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


        feedFragmentViewModel.getShareLink().observe(this.getViewLifecycleOwner(), new EventObserver<>(new EventListener<String>() {
            @Override
            public void onEvent(String s) {
                ShareCompat.IntentBuilder shareBuilder = ShareCompat.IntentBuilder.from(getActivity());
                shareBuilder.setChooserTitle(R.string.share_post)
                        .setSubject(getString(R.string.share_post))
                        .setText(getString(R.string.hey_check_out_post)+s)
                        .setType("text/plain");

                Log.d(TAG, "onChanged: "+s);

                if(s!=null && !s.isEmpty()) {
                    Intent intent = shareBuilder.createChooserIntent();
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                        startActivity(intent);
                }
            }
        }));


        //create options object
/*
        options = new FirestorePagingOptions.Builder<FeedPost>()
                .setLifecycleOwner(FeedFragment.this)
                .setQuery(feedFragmentViewModel.getUserFeedQuery(), config, FeedPost.class)
                .build();
*/



        //create document adapter with options
        fireStoreFeedDocumentPagingAdapter = new FireStoreFeedDocumentPagingAdapter(dashboardFragmentViewModel.getOptions(), this, dashboardFragmentViewModel.getUserAuthId());

        fragmentFeedBinding.cardList.setAdapter(fireStoreFeedDocumentPagingAdapter);

        dashboardFragmentViewModel.getFeedPageCount().observe(this.getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                Log.d(TAG, "onChanged: "+integer);
                if(integer==null)
                    fragmentFeedBinding.emptyStateLayout.setVisibility(View.VISIBLE);


                if(integer>0)
                    fragmentFeedBinding.emptyStateLayout.setVisibility(View.GONE);
                else
                    fragmentFeedBinding.emptyStateLayout.setVisibility(View.VISIBLE);

            }
        });
/*        fireStoreFeedDocumentPagingAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);


                if(fireStoreFeedDocumentPagingAdapter.getItemCount()>0)
                    fragmentFeedBinding.emptyStateLayout.setVisibility(View.GONE);
                else
                    fragmentFeedBinding.emptyStateLayout.setVisibility(View.VISIBLE);
            }
        });*/

        fireStoreFeedDocumentPagingAdapter.addLoadStateListener(new PagedList.LoadStateListener() {

            @Override
            public void onLoadStateChanged(@NonNull PagedList.LoadType type, @NonNull PagedList.LoadState state, @Nullable Throwable error) {
                //todo show the loaders
                Log.d(TAG, "onLoadStateChanged: "+state.name());
                //fragmentFeedBinding.emptyStateLayout.setVisibility(View.GONE);

                switch (state){

                    case IDLE:


                        break;


                    case LOADING:
                        // The adapter has started to load an additional page
                        // ...
                        /*fragmentFeedBinding.cardListShim.startShimmer();
                        fragmentFeedBinding.cardListShim.setVisibility(View.VISIBLE);*/
                        //fragmentFeedBinding.emptyStateLayout.setVisibility(View.GONE);

                        Log.d(TAG, "loading: "+ fireStoreFeedDocumentPagingAdapter.getItemCount());

                        break;

                    case DONE:
                        // The previous load (either initial or additional) completed
                        // ...

                        dashboardFragmentViewModel.getFeedPageCount().setValue(fireStoreFeedDocumentPagingAdapter.getItemCount());


                        Log.d(TAG, "done: "+        fireStoreFeedDocumentPagingAdapter.getItemCount());

                        fragmentFeedBinding.cardListShim.stopShimmer();
                        fragmentFeedBinding.cardListShim.setVisibility(View.GONE);

                        break;

                    case ERROR:
                        // The previous load (either initial or additional) failed. Call
                        // the retry() method in order to retry the load operation.

                        break;
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
            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.profileFragment);
        else{
            DashboardFragmentDirections.ActionDashboardFragmentToShowProfileFragment action =
                    DashboardFragmentDirections.actionDashboardFragmentToShowProfileFragment(feedPost.getUserId());

            dashboardFragmentViewModel.setNavigationCommandMutableLiveData(new NavigationCommand.NavigationAction(action));
        }
    }

    @Override
    public void onFeedPostShareTapped(FeedPost feedPost) {
        //todo to post dialog
        //DashboardFragmentDirections.actionDashboardFragmentToShowPostFragment()
        //dashboardFragmentViewModel.setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(R.id.action_global_loadingDialog));
        feedFragmentViewModel.generateLink(feedPost);
        //dashboardFragmentViewModel.setNavigationCommandMutableLiveData(new NavigationCommand.NavigationId(0));

    }


}
