package com.codedevtech.portfolioapp.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private String chatroomId;

    public ChatroomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatroomId = ChatroomFragmentArgs.fromBundle(getArguments()).getChatroomId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentChatroomBinding fragmentBinding = FragmentChatroomBinding.inflate(inflater, container, false);

        fragmentBinding.messageEditText.setText("lol");
        ChatroomViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(ChatroomViewModel.class);

        fragmentBinding.setLifecycleOwner(this.getViewLifecycleOwner());
        fragmentBinding.setViewmodel(viewModel);

        return fragmentBinding.getRoot();


    }


}
