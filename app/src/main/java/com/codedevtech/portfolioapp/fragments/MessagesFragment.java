package com.codedevtech.portfolioapp.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentMessagesBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.viewmodels.DashboardFragmentViewModel;
import com.codedevtech.portfolioapp.viewmodels.MessagesFragmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewmodelFactory;
    private MessagesFragmentViewModel messagesFragmentViewModel;
    private DashboardFragmentViewModel dashboardFragmentViewModel;
    private FloatingActionButton newMessage;


    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        newMessage.hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        newMessage.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMessagesBinding fragmentMessagesBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_messages, container, false);
        messagesFragmentViewModel = ViewModelProviders.of(this, viewmodelFactory).get(MessagesFragmentViewModel.class);
        dashboardFragmentViewModel = ViewModelProviders.of(getParentFragment().getParentFragment().getParentFragment(), viewmodelFactory).get(DashboardFragmentViewModel.class);

        fragmentMessagesBinding.setLifecycleOwner(this.getViewLifecycleOwner());


        newMessage = fragmentMessagesBinding.newMessageFAB;

        return fragmentMessagesBinding.getRoot();
    }

}
