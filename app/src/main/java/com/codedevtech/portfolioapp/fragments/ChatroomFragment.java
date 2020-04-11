package com.codedevtech.portfolioapp.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.databinding.FragmentChatroomBinding;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.codedevtech.portfolioapp.viewmodels.ChatroomViewModel;

import javax.inject.Inject;

public class ChatroomFragment extends Fragment implements Injectable {
    private static final String TAG = "ChatroomFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public ChatroomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentChatroomBinding fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chatroom, container, false);
        ChatroomViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(ChatroomViewModel.class);

        fragmentBinding.setLifecycleOwner(this.getViewLifecycleOwner());
        fragmentBinding.setViewmodel(viewModel);

        return fragmentBinding.getRoot();


    }


}
