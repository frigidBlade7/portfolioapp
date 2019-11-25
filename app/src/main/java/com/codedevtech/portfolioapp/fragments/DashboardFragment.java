package com.codedevtech.portfolioapp.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedevtech.portfolioapp.R;
import com.codedevtech.portfolioapp.di.interfaces.Injectable;
import com.facebook.share.Share;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements Injectable{

    private static final String TAG = "DashboardFragment";

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Inject
    ViewModelProvider.Factory viewmodelFactory;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}
